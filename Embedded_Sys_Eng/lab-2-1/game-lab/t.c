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
#include "vid.c"
#include "uart.c"

extern char _binary_panda_bmp_start;
extern char _binary_porkcar_bmp_start;
extern char _binary_pacman_bmp_start;
//_binary_pacman_bmp_end
/// A _binary_pacman_bmp_size
// D _binary_pacman_bmp_start


int color;

/*** this is for converted images with |h|w|pixels| format ****
int show(char *p, int startRow)
{ 
   int h, w, row, col, pixel; 
   unsigned char r, g, b;

   h = (*(p+3)<<24) + (*(p+2)<<16) + (*(p+1)<<8) + *p;
   p += 4;
   w = (*(p+3)<<24) + (*(p+2)<<16) + (*(p+1)<<8) + *p;
   p += 4;          // skip over 8 byes

   uprintf("h=%d w=%d\n", h, w);
   //  if (h > 480) h = 480;
   //if (w > 640) w = 640;

   row = startRow; col = 0;
   while(1){
     r = *p; g = *(p+1); b = *(p+2);
     pixel = (b<<16) + (g<<8) + r;
     //     fb[row*640 + col] = pixel;
     fb[row*WIDTH + col] = pixel;
     p += 3;         // advance p by 3 bytes
     col++;
     if (col >= w){  // to line width of jpg image
        col = 0;
        row++;
     }
     if (row >= h+startRow)
        break;
   }
}
*******************************************/
extern int replacePix;
int main()
{
   char c, *p;
   int mode;
   uart_init();
   up = upp[0];
   int x=80;
   int y=0;
   mode = 0;
   fbuf_init(mode);
 p = &_binary_panda_bmp_start;
   show_bmp1(p, 0, 80); 

    p = &_binary_pacman_bmp_start;
   show_bmp(p, 0, 80); 
   int nomove=0;
int key;
   while(1){
     uprintf("enter a key from this UART : ");
     nomove=0;
     replacePix =1;
     key=ugetc(up);
// TODO
// add more keys for up,left,right (using WASD keys for movement)
     switch(key){

      // w for up
      case 'w':
         if ( y > 0 )
            y-=10;
         break;

      // a for left
      case 'a':
         if ( x > 0 )
            x-=10;
         break;

      // s for down (previosuly was e)
      case 's':
         if ( y < 480-16 )
            y+=10;
         break;

      // d for right
      case 'd':
         if ( x < 640-16 )
            x+=10;
         break;
      default:
        nomove=1;
      }
      if (!nomove)
         show_bmp(p, y, x);
   }
   while(1);   // loop here  
}
