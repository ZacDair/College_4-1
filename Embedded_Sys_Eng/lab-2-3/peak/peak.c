#include "Stream.h"

//Implementation for identifying if there are n peaks in the data
int peak(struct Stream *s, int n){
int prev;
int c =0;
int state =0;
int finish =0;
//printf("peak1 cnt = %d size =%d\n",s->cnt,s->size);
if (n==0) return 0;
prev = streamGet(s,&finish);
//printf("peak2 cnt = %d size =%d\n",s->cnt,s->size);
if (finish ) return 0;
int curr = streamGet(s,&finish);
while (!finish && c<n ){
//printf("here1 state = %d\n",state);
//printf("peak3 c=%d cnt = %d size =%d\n",c,s->cnt,s->size);
   switch (state){
      case 0:
         if (prev < curr)
            state = 1;
         break;
      case 1:
         if (prev > curr){
            state = 0;
            c++; // found a peak 
         }
        break;
      }
prev = curr;
curr = streamGet(s,&finish);
}
if (c==n) return 1;
return 0;
}

//Implementation for identifying if there are n valleys in the data
int valley(struct Stream *s, int n){
int prev;
int c =0;
int state =0;
int finish =0;
prev = streamGet(s,&finish);
if (finish ) return 0;
int curr = streamGet(s,&finish);
while (!finish && c<n ){
   switch (state){
      case 0:
         if (prev > curr)
            state = 1;
         break;
      case 1:
         if (prev < curr){
            state = 0;
            c++; // found a valley 
         }
        break;
      }
prev = curr;
curr = streamGet(s,&finish);
}
if (c==n) return 1;
return 0;
}

//Implementation for identifying if there are n increasing peaks in the data
int increasingPeak(struct Stream *s, int n){
int prev;
int c =0;
int state =0;
int finish =0;
//printf("peak1 cnt = %d size =%d\n",s->cnt,s->size);
if (n==0) return 0;
prev = streamGet(s,&finish);
//printf("peak2 cnt = %d size =%d\n",s->cnt,s->size);
if (finish ) return 0;
int curr = streamGet(s,&finish);
while (!finish && c<n ){
//printf("here1 state = %d\n",state);
//printf("peak3 c=%d cnt = %d size =%d\n",c,s->cnt,s->size);
   switch (state){
      //initial stationary or decreasing mode
      case 0:
         if (prev < curr)
            state = 1;
         break;
        
      //increasing before first potential peak
      case 1:
         if (prev > curr){
            state = 2;
         }
        break;

      //decreasing after a peak
      case 2:
         if (prev < curr)
            state = 3;
            c++;
         break;

      //increasing after a peak
      case 3:
         if (prev > curr){
            state = 2;
         }
        break;
      }
prev = curr;
curr = streamGet(s,&finish);
}
if (c==n) return 1;
return 0;
}

