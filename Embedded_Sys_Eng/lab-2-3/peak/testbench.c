
#include "Stream.h"
int streamGet(struct Stream *s,int * finished){
    *finished =0;
 //printf("streamGet cnt = %d size =%d\n",s->cnt,s->size);
    if (s->cnt < s->size){
      // printf("streamGet %d\n",s->buffer[s->cnt]);
       return s->buffer[s->cnt++];
    }
    else
       *finished = 1;
return -1;
}

/*
peak examples from
http://sofdem.github.io/gccat/gccat/Cpeak.html
(2,⟨1,1,4,8,6,2,7,1⟩)
(0,⟨1,1,4,4,4,6,7,7⟩)
(4,⟨1,5,4,9,4,6,2,7,6⟩)

valley examples from
http://sofdem.github.io/gccat/gccat/Cvalley.html
(1,⟨1,1,4,8,6,2,7,1⟩)
(0,⟨1,1,4,5,8,8,4,1⟩)
(4,⟨1,0,4,0,8,2,4,1,2⟩)

increasing peak examples from
http://sofdem.github.io/gccat/gccat/Cincreasing_peak.html
(⟨1,5,5,3,5,2,2,7,4⟩)
*/
main(){
printf("\n");

//Peak Constraint
int data1[] = {1,1,4,8,6,2,7,1};
int data2[] ={1,5,4,9,4,6,2,7,6};
struct Stream s;

s.buffer = data1;
s.size = 8;
s.cnt =0;

int val = peak(&s,2);
printf("result for peak({1,1,4,8,6,2,7,1},2) i.e. are there 2 peaks in the data? %d\n",val);

s.buffer = data2;
s.size = 9;
s.cnt =0;

val = peak(&s,4);
printf("result for peak({1,5,4,9,4,6,2,7,6},4) i.e. are there 4 peaks in the data? %d\n",val);

val = peak(&s,5);
printf("result for peak({1,5,4,9,4,6,2,7,6},5) i.e. are there 5 peaks in the data? %d\n",val);

//Valley Constraint
int valleyData1[] = {1,1,4,8,6,2,7,1};
int valleyData2[] = {1,1,4,5,8,8,4,1};
int valleyData3[] = {1,0,4,0,8,2,4,1,2};

s.buffer = valleyData1;
s.size = 8;
s.cnt =0;

val = valley(&s,1);
printf("result for valley({1,1,4,8,6,2,7,1},1) i.e. is there 1 valley in the data? %d\n",val);

s.buffer = valleyData2;
s.size = 8;
s.cnt =0;

val = valley(&s,0);
printf("result for valley({1,1,4,5,8,8,4,1},0) i.e. are there 0 valleys in the data? %d\n",val);

val = valley(&s,1);
printf("result for valley({1,1,4,5,8,8,4,1},1) i.e. are there 1 valley in the data? %d\n",val);

s.buffer = valleyData3;
s.size = 9;
s.cnt =0;

val = valley(&s,4);
printf("result for valley({1,0,4,0,8,2,4,1,2},4) i.e. are there 4 valleys in the data? %d\n",val);

//Increasing Peak Constraint
int increasingPeakData1[] = {1,5,5,3,5,2,2,7,4};
int increasingPeakData2[] = {8,7,7,6,6,6,4,3,2};

s.buffer = increasingPeakData1;
s.size = 9;
s.cnt =0;

val = increasingPeak(&s,1);
printf("result for increasingPeak({1,5,5,3,5,2,2,7,4}) i.e. is there an increasing peak in the data? %d\n",val);

s.buffer = increasingPeakData2;
s.size = 9;
s.cnt =0;

val = increasingPeak(&s,1);
printf("result for increasingPeak({8,7,7,6,6,6,4,3,2}) i.e. is there an increasing peak in the data? %d\n",val);
}

