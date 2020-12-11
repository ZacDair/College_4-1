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
struct sprite {
   int x,y;
   int buff[16][16];
   int replacePix;
   char *p;
   int oldstartRow;
   int oldstartCol;
   int waypoints;
   int way;
  
};

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



enum Objet {V, M, P, W};
#define Width  19
#define Height 21
//Tableau de la carte
enum Objet table[Height][Width] =
{
	{ M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M, M},
	{ M, P, P, P, P, P, P, P, P, M, P, P, P, P, P, P, P, P, M},
	{ M, W, M, M, P, M, M, M, P, M, P, M, M, M, P, M, M, W, M},
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
#define sgn(x) ((x<0)?-1:((x>0)?1:0))
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
   sprites[1].x = 16*4;
   sprites[1].y = 16*4;
   sprites[1].replacePix =0;
   sprites[1].p = &_binary_pokey_bmp_start;
   sprites[2].x = 16*4;
   sprites[2].y = 16*8;
   sprites[2].replacePix =0;
   sprites[2].p = &_binary_power_bmp_start;

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
int j;
for (j=0;j<Height;j++)
    for (i=0;i<Width;i++){
        if (table[j][i] == M){
           show_bmp1(&_binary_mur_bmp_start, j*16, i*16);
           }
	if (table[j][i] == P){
           show_bmp1(&_binary_point_bmp_start, j*16, i*16);
           }
}
   //show_bmp1(p, 0, 80); 

    p = &_binary_pacman_bmp_start;
   show_bmp(p, y, x,buff,replacePix,&oldstartR,&oldstartC);
 
  
   unlock();
 
int move=0;
int key;
int hit=0;


   while(1){
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
         if (table[y-16>>4][x>>4] == M){
            move=0;
            break;
         }
         y-=16;
         move=1;
         break;
	
	    // Go right 'a'
	    case 'a':
         if ( x > 0 )
         if (table[y>>4][x-16>>4] == M){
            move=0;
            break;
         }
         x-=16;
         move=1;
         break;
	
	    // Go down 's'
	    case 's':
         if ( y< 600 )
         if (table[y+16>>4][x>>4] == M){
            move=0;
            break;
         }
         y+=16;
         move=1;
         break;
	
	    // Go left 'd'
	    case 'd':
         if ( x< 400 )
         if (table[y>>4][x+16>>4] == M){
            move=0;
            break;
         }
         x+=16;
         move=1;
         break;
       

      default:
        move=0;
      }
    }
    
      if (move){
        
	 
         if (table[y>>4][x>>4] == P){
             black_point(y,x);
             table[y>>4][x>>4] = V;
             uprintf("point\n");
         }
         show_bmp(p, y, x,buff,replacePix,&oldstartR,&oldstartC);
      }
      if (spriteMove){
         sprites[0].x+=16;
          show_bmp(sprites[0].p, sprites[0].y, sprites[0].x,sprites[0].buff,sprites[0].replacePix,
         &(sprites[0].oldstartRow),&(sprites[0].oldstartCol));
        
         speed=0;
         spriteMove =0;
         

         sprites[0].replacePix=1;
      }
   
   }
}
