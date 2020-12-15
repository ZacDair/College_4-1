/********************************************************************
Copyright 2010-2017 K.C. Wang, <kwang@eecs.wsu.edu>
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
********************************************************************/

#include "defines.h"
#include "string.c"




int spriteMove = 0;
char *tab = "0123456789ABCDEF";
int color;


#include "timer.c"

#include "interrupts.c"
#include "kbd.c"
#include "uart.c"
#include "vid.c"



extern char _binary_panda_bmp_start;
extern char _binary_pacman_bmp_start;
extern char _binary_speedy_bmp_start;
extern char _binary_pokey_bmp_start;
extern char _binary_shadow_bmp_start;
extern char _binary_power_bmp_start;
extern char _binary_mur_bmp_start;
extern char _binary_point_bmp_start;

// Additional bmps for UI elements (Not working, due to the files being indexed bmps instead of RGB)
extern char _binary_lives_bmp_start;
extern char _binary_gameover_bmp_start;
extern char _binary_gamewon_bmp_start;




enum Objet {V, M, P, W};
#define Width  19
#define Height 21
//Tableau de la carte
enum Objet table[Height][Width] =
        {
                { M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M},
                { M, P, P, P, P, P, P, P, P, M, P, P, P, P, P, P, P, P, M},
                { M, M, M, M, P, M, M, M, P, M, P, M, M, M, P, M, M, W, M},
                { M, P, P, P, P, P, P, P, P, P, P, P, P, P, P, P, P, P, M},
                { M, P, M, M, P, M, P, M, M, M, M, M, P, M, P, M, M, P, M},
                { M, P, P, P, P, M, P, P, P, M, P, P, P, M, P, P, P, P, M},
                { M, M, M, M, P, M, M, M, V, M, V, M, M, M, P, M, M, M, M},
                { V, V, V, M, P, M, V, V, V, V, V, V, V, M, P, M, V, V, V},
                { M, M, M, M, P, M, V, M, M, V, M, M, V, M, P, M, M, M, M},
                { V, V, V, V, P, V, V, M, V, V, V, M, V, V, P, V, V, V, V},
                { M, M, M, M, P, M, V, M, M, M, M, M, V, M, P, M, M, M, M},
                { V, V, V, M, P, M, V, V, V, V, V, V, V, M, P, M, V, V, V},
                { M, M, M, M, P, M, V, M, M, M, M, M, V, M, P, M, M, M, M},
                { M, P, P, P, P, P, P, P, P, M, P, P, P, P, P, P, P, P, M},
                { M, P, M, M, P, M, M, M, P, M, P, M, M, M, P, M, M, P, M},
                { M, W, P, M, P, P, P, P, P, P, P, P, P, P, P, M, P, W, M},
                { M, M, P, M, P, M, P, M, M, M, M, M, P, M, P, M, P, M, M},
                { M, P, P, P, P, M, P, P, P, M, P, P, P, M, P, P, P, P, M},
                { M, P, M, M, M, M, M, M, P, M, P, M, M, M, M, M, M, P, M},
                { M, P, P, P, P, P, P, P, P, P, P, P, P, P, P, P, P, P, M},
                { M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M},
        };

#include "dijkstra.c"
struct sprite {
    int x,y;
    int buff[16][16];
    int replacePix;
    char *p;
    int oldstartRow;
    int oldstartCol;
    int pathpos;
    int pathlen;
    int pathV[NoV];
    int path;
};
struct sprite sprites[3];
void copy_vectors(void) {
    extern u32 vectors_start;
    extern u32 vectors_end;
    u32 *vectors_src = &vectors_start;
    u32 *vectors_dst = (u32 *)0;

    while(vectors_src < &vectors_end)
        *vectors_dst++ = *vectors_src++;
}
int kprintf(char *fmt, ...);
void timer_handler();

void uart0_handler()
{
    uart_handler(&uart[0]);
}
void uart1_handler()
{
    uart_handler(&uart[1]);
}

// IRQ interrupts handler entry point
//void __attribute__((interrupt)) IRQ_handler()
// timer0 base=0x101E2000; timer1 base=0x101E2020
// timer3 base=0x101E3000; timer1 base=0x101E3020
// currentValueReg=0x04
TIMER *tp[4];

void IRQ_handler()
{
    int vicstatus, sicstatus;
    int ustatus, kstatus;

    // read VIC SIV status registers to find out which interrupt
    vicstatus = VIC_STATUS;
    sicstatus = SIC_STATUS;
    //kprintf("vicstatus=%x sicstatus=%x\n", vicstatus, sicstatus);
    // VIC status BITs: timer0,1=4, uart0=13, uart1=14, SIC=31: KBD at 3
    /**************
    if (vicstatus & 0x0010){   // timer0,1=bit4
      if (*(tp[0]->base+TVALUE)==0) // timer 0
         timer_handler(0);
      if (*(tp[1]->base+TVALUE)==0)
         timer_handler(1);
    }
    if (vicstatus & 0x0020){   // timer2,3=bit5
       if(*(tp[2]->base+TVALUE)==0)
         timer_handler(2);
       if (*(tp[3]->base+TVALUE)==0)
         timer_handler(3);
    }
    if (vicstatus & 0x80000000){
       if (sicstatus & 0x08){
          kbd_handler();
       }
    }
    *********************/
    /******************
    if (vicstatus & (1<<4)){   // timer0,1=bit4
      if (*(tp[0]->base+TVALUE)==0) // timer 0
         timer_handler(0);
      if (*(tp[1]->base+TVALUE)==0)
         timer_handler(1);
    }
    if (vicstatus & (1<<5)){   // timer2,3=bit5
       if(*(tp[2]->base+TVALUE)==0)
         timer_handler(2);
       if (*(tp[3]->base+TVALUE)==0)
         timer_handler(3);
    }
    *********************/
    if (vicstatus & (1<<4)){   // timer0,1=bit4
        timer_handler(0);
    }
    if (vicstatus & (1<<12)){   // bit 12: uart0 
        uart0_handler();
    }
    if (vicstatus & (1<<13)){   // bit 13: uart1
        uart1_handler();
    }

    if (vicstatus & (1<<31)){
        if (sicstatus & (1<<3)){
            //   kbd_handler();
        }
    }
}

int abs(int a){
    return a<0 ? -a:a;
}
extern int oldstartR;
extern int oldstartC;
extern int replacePix;
extern int buff[16][16];
struct sprite sprites[3];

int main()
{
    int i;
    char line[128], string[32];
    UART *up;

    // start pacman at 3,1 in the map
    int x=16;
    int y=3*16;
    int x1,y1;
    sprites[0].x = 16;
    sprites[0].y = 16;
    sprites[0].replacePix =0;
    sprites[0].p = &_binary_speedy_bmp_start;
    sprites[1].x = 16*10;
    sprites[1].y = 16*3;
    sprites[1].replacePix =0;
    sprites[1].p = &_binary_pokey_bmp_start;
    sprites[2].x = 16*9;
    sprites[2].y = 16*9;
    sprites[2].replacePix =0;
    sprites[2].p = &_binary_shadow_bmp_start;

    char * p;
    color = YELLOW;
    row = col = 0;
    fbuf_init();

    /* enable timer0,1, uart0,1 SIC interrupts */
    VIC_INTENABLE |= (1<<4);  // timer0,1 at bit4
    VIC_INTENABLE |= (1<<5);  // timer2,3 at bit5

    VIC_INTENABLE |= (1<<12); // UART0 at 12
    VIC_INTENABLE |= (1<<13); // UART1 at 13

    UART0_IMSC = 1<<4;  // enable UART0 RXIM interrupt
    UART1_IMSC = 1<<4;  // enable UART1 RXIM interrupt

    VIC_INTENABLE |= 1<<31;   // SIC to VIC's IRQ31

    /* enable KBD IRQ */
    //SIC_ENSET = 1<<3;  // KBD int=3 on SIC
    //SIC_PICENSET = 1<<3;  // KBD int=3 on SIC

    // kprintf("C3.3 start: Interrupt-driven drivers for Timer KBD UART\n");
    timer_init();
    /***********
    for (i=0; i<4; i++){
       tp[i] = &timer[i];
       timer_start(i);
    }
    ************/
    timer_start(0);
    kbd_init();

    uart_init();
    up = &uart[0];
    
    //Create a variable to store the total amount of points to collect
    int totalPointCount = 0;
    for (int j=0;j<Height;j++)
        for (int i=0;i<Width;i++){
            if (table[j][i] == M){
                show_bmp1(&_binary_mur_bmp_start, j*16, i*16);
            }
            else if (table[j][i] == P){
                show_bmp1(&_binary_point_bmp_start, j*16, i*16);
                //When we draw a point bmp, also increment our count of points
                totalPointCount++;
            }
	    //If the cell is a powerup draw that sprite
	    else if (table[j][i] == W){
		show_bmp1(&_binary_power_bmp_start, j*16, i*16);
	    }
	    
        }
    //show_bmp1(p, 0, 80);
    //Show pacman and the enemy sprites
    p = &_binary_pacman_bmp_start;
    show_bmp(p, y, x,buff,replacePix,&oldstartR,&oldstartC);
    show_bmp(sprites[0].p, sprites[0].y, sprites[0].x,sprites[0].buff,sprites[0].replacePix,
             &(sprites[0].oldstartRow),&(sprites[0].oldstartCol));
    show_bmp(sprites[1].p, sprites[1].y, sprites[1].x,sprites[1].buff,sprites[1].replacePix,
             &(sprites[1].oldstartRow),&(sprites[1].oldstartCol));
    show_bmp(sprites[2].p, sprites[2].y, sprites[2].x,sprites[2].buff,sprites[2].replacePix,
             &(sprites[2].oldstartRow),&(sprites[2].oldstartCol));

    //Show 3 pacman icons as lives
    int lifeIconSize = 16;
    //show_bmp1(&_binary_won_bmp_start, lifeIconSize * 4, 80*2);
    show_bmp1(p, lifeIconSize * 5, lifeIconSize * 37);
    show_bmp1(p, lifeIconSize * 6, lifeIconSize * 37);
    show_bmp1(p, lifeIconSize * 7, lifeIconSize * 37);

/* TODO manually set the following two edges for the drawing below. Remove. */
//graph[21][22] =1;
//graph[22][23] =1;
    for (int j=0;j<Height;j++){
        for (int i=0;i<Width;i++){
            // Construct the edges in the graph
            if (table[j][i] !=M  ){
                if (i+1<Width && table[j][i+1]!= M ) {
/*TODO */
                    // need and edge from the vertex associated with table[j][i] to the vertex associated
                    // with table[j][i+1]
                    // graph[n][m] = 1; creates an edge from vertex n to vertex m
                    // the unique vertex for a square that pacman or the ghost at j,i  is j*Width +i
                    graph[j*Width + i][(j*Width + i)+1] = 1;

                }
                if (j+1<Height && table[j+1][i]!=M ){
/*TODO */
                    // need and edge from the vertex associated with table[j][i] to the vertex associated
                    // with table[j+1][i]
                    // graph[n][m] = 1; creates an edge from vertex n to vertex m
                    graph[j*Width + i][(j*Width + i)+Width] = 1;

                }
            }
        }
    }
/* the following code will draw in a line for every edge set can be removed */
    for (int j=0;j<NoV;j++)
        for (int i=0;i<NoV;i++)
            if (graph[j][i]==1){
                uprintf("%d %d\n",j,i);
                if (i > j?i-j<Width:j-i<Width)
                    line_fast((j%Width)*16+12, (j/Width)*16+4, (i%Width)*16+4, (i/Width)*16+4);
                else
                if (i > j?i-j>=Width:j-i>=Width)
                    line_fast((j%Width)*16+4, (j/Width)*16+12, (i%Width)*16+4, (i/Width)*16+4);
                graph[i][j] =2;
            }
    unlock();

    int move=0;
    int key;
    int hit=0;
    int dead =0;
    int lives =3;
    int score = 0;
    int poweredUp = 0;
    //Run always unless we run out of lives or collect all points
    while(1 && lives != 0 && score != totalPointCount){
        //uprintf("enter a key from this UART : ");
//uprintf("rand %d\n",(rand()+1)%5);
        move=0;
        replacePix =1;
        if (upeek(up)){
            key=ugetc(up);
            switch(key){
                    // Go up 'w'
                case 'w':
                    if ( y > 0 )
                        if (table[y-16>>4][x>>4] != M){
                            y-=16;
                            move=1;
                            break;
                        }
                    break;

                    // Go right 'a'
                case 'a':
                    if ( x > 0 )
                        if (table[y>>4][x-16>>4] != M){
                            x-=16;
                            move=1;
                            break;
                        }
                    break;

                    // Go down 's'
                case 's':
                    if ( y< 600 )
                        if (table[y+16>>4][x>>4] != M){
                            y+=16;
                            move=1;
                            break;
                        }
                    break;

                    // Go left 'd'
                case 'd':
                    if ( x< 400 )
                        if (table[y>>4][x+16>>4] != M){
                            x+=16;
                            move=1;
                            break;
                        }
                    break;


                default:
                    move=0;
            }
        }
	
        if (move){


            if (table[y>>4][x>>4] == P){
                black_point(y,x);
                table[y>>4][x>>4] = V;
                score++;
                uprintf("Point collected! %d/%d !\n", score, totalPointCount);
            }
	    else if (table[y>>4][x>>4] == W){
                black_point1(y,x);
                table[y>>4][x>>4] = V;
		poweredUp = 1;
	    }
            show_bmp(p, y, x,buff,replacePix,&oldstartR,&oldstartC);
        }

        if (spriteMove){
            // Counter and a while loop to cycle through the enemies moving each (counter variable used as an index)
            int counter = 0;
            while (counter < 3){
                if (sprites[counter].x == x && sprites[counter].y == y ){
                    if (!dead && lives != 0){
                        uprintf("\n\nYou died! %d lives left!\n", lives-1);
                        // Redraw where one of the lives was as a blank space
                        for (int i=0; i<16; i++){
                            for (int j=0; j<16; j++){
                                // Remove a life icon
                                clrpix((16*37)+i, (16*(lives+4))+j);

                                // Remove all enemy sprites
                                clrpix((sprites[0].x)+i, (sprites[0].y)+j);
                                clrpix((sprites[1].x)+i, (sprites[1].y)+j);
                                clrpix((sprites[2].x)+i, (sprites[2].y)+j);
                            }
                        }

                        // Reset all enemy spawnpoints and clear paths
                        sprites[0].x = 16*1;
                        sprites[0].y = 16*1;
                        sprites[0].path =0;
                        sprites[1].x = 16*17;
                        sprites[1].y = 16*1;
                        sprites[1].path =0;
                        sprites[2].x = 16*17;
                        sprites[2].y = 16*19;
                        sprites[2].path =0;
                        lives--;
                    }
                    dead=1;
                    continue;
                }
                //Stop sprites moving if we have no lives left or score is reached
                if(lives == 0 || score == totalPointCount){
                    spriteMove = 0;
                } 
                dead = 0;
                if (!sprites[counter].path){
                    // TODO calculate the vertex where the ghost is and the vertex where pacman is and
                    // call dijkstra accordingly - hint y>>4 divides y by 16 and x>>4 divides x by 16.
                    // Note that the x,y is the absolute pixel position of the ghost or pacman respectively.
                    // The dijkstra function will fill the soln
                    // array with the vertices of the optimal path. solnlen will hold the number of vertices in the path.
                    // If solnlen is zero then there is no path.
                    //
                    int vpac = ((y>>4)*Width)+(x>>4);
                    int vghost = ((sprites[counter].y>>4)*Width)+(sprites[counter].x>>4);
                    dijkstra(graph, vghost,vpac );
                    if (solnlen){
                        // copy the solution to the ghost's path list
                        for (int i=0;i<solnlen;i++)
                            sprites[counter].pathV[i] = soln[i];
                        /* TODO set the x,y position of the ghost to absolute pixel position associated with the
                               first vertex in the soln path.
                               Note:- the unique vertex for a square that pacman or the ghost at j,i  is j*Width +i
                               Note also that x,y is the absolute pixel position of the ghost
                    */
                        sprites[counter].x = (soln[i]%Width)*16;
                        sprites[counter].y = (soln[i]/Width)*16;
                        sprites[counter].pathpos=1;
                        sprites[counter].pathlen=solnlen;
                        sprites[counter].path =1;
                    }
                    else{
                        uprintf("no soln");
                        sprites[counter].x+=16;
                    }
                }
                else
                if (sprites[counter].pathpos == sprites[counter].pathlen)
                    sprites[counter].path=0; // get new path next time
                else
                if (sprites[counter].pathpos < sprites[counter].pathlen){

                    int pos = sprites[counter].pathpos;
                    int vertex = sprites[counter].pathV[pos];
                    /* TODO set the x,y position of the ghost to x,y position associated with the
                               next vertex in the path.
                               Note:- the unique vertex for a square that pacman or the ghost at j,i  is j*Width +i
                    */
                    // For each sprite, check vertexes to prevent collision
                    switch (counter){
                        case 0:
                            if(vertex != sprites[counter+1].pathV[sprites[counter+1].pathpos-1]){
                                if(vertex != sprites[counter+2].pathV[sprites[counter+2].pathpos-1]){
                                    sprites[counter].x = (vertex%Width)*16;
                                    sprites[counter].y = (vertex/Width)*16;
                                    sprites[counter].pathpos++;
                                }
                            }
                            break;

                        case 1:
                            if(vertex != sprites[counter-1].pathV[sprites[counter-1].pathpos-1]){
                                if(vertex != sprites[counter+1].pathV[sprites[counter+1].pathpos-1]){
                                    sprites[counter].x = (vertex%Width)*16;
                                    sprites[counter].y = (vertex/Width)*16;
                                    sprites[counter].pathpos++;
                                }
                            }
                            break;

                        case 2:
                            if(vertex != sprites[counter-1].pathV[sprites[counter-1].pathpos-1]){
                                if(vertex != sprites[counter-2].pathV[sprites[counter-2].pathpos-1]){
                                    sprites[counter].x = (vertex%Width)*16;
                                    sprites[counter].y = (vertex/Width)*16;
                                    sprites[counter].pathpos++;
                                }
                            }
                            break;
                    }
                    //uncomment the line below to calculate a new path every move
                    //sprites[counter].path=0;
                    
                

               }
    //         sprites[0].x+=16;
                sprites[counter].replacePix=1;
                show_bmp(sprites[counter].p, sprites[counter].y, sprites[counter].x,sprites[counter].buff,sprites[counter].replacePix,
                         &(sprites[counter].oldstartRow),&(sprites[counter].oldstartCol));

                speed=0;
                spriteMove =0;
                counter++;
                
            }
        
        }
    }
    if (score == totalPointCount){
        uprintf("\n\nCongratulations, You Won!!!");
    }
    else{
        uprintf("\n\nGame Over!!!");
    }
}
