#include <stdint.h>
 
typedef volatile struct {
 uint32_t DR;
 uint32_t RSR_ECR;
 uint8_t reserved1[0x10];
 const uint32_t FR;
 uint8_t reserved2[0x4];
 uint32_t LPR;
 uint32_t IBRD;
 uint32_t FBRD;
 uint32_t LCR_H;
 uint32_t CR;
 uint32_t IFLS;
 uint32_t IMSC;
 const uint32_t RIS;
 const uint32_t MIS;
 uint32_t ICR;
 uint32_t DMACR;
} pl011_T;
 
enum {
 RXFE = 0x10,
 TXFF = 0x20,
};
 
pl011_T * const UART0 = (pl011_T *)0x101f1000;
//pl011_T * const UART1 = (pl011_T *)0x101f2000;
//pl011_T * const UART2 = (pl011_T *)0x101f3000;

//Traffic Code
struct State {
    unsigned long Out;
    unsigned long Time;
    unsigned long Next[4];};
typedef const struct State STyp;

#define goN   0
#define waitN 1
#define goE   2
#define waitE 3

STyp FSM[4]={
        {0x21,3000,{goN,waitN,goN,waitN}},
        {0x22, 500,{goE,goE,goE,goE}},
        {0x0C,3000,{goE,goE,waitE,waitE}},
        {0x14, 500,{goN,goN,goN,goN}}};

unsigned long S;  // index to the current state

unsigned long Input=0;

int OldOutput = 0;
 
static inline char upperchar(char c) {
 if((c >= 'a') && (c <= 'z')) {
  return c - 'a' + 'A';
 } else {
  return c;
 }
}

void print_uart0(const char *s){
    while(*s != '\0'){
        UART0->DR = (unsigned int)(*s);
        s++; 
    }
}
static void uart_echo(pl011_T *uart) {
 int lightVal = FSM[S].Out;
 if(lightVal != OldOutput) {
  if (lightVal == 33) {
   print_uart0("North Green\n");
  } else if (lightVal == 34) {
   print_uart0("North Red\n");
  } else if (lightVal == 12) {
   print_uart0("East Green\n");
  } else if (lightVal == 20) {
   print_uart0("East Red\n");
  }
 }
 OldOutput = lightVal;
 if ((uart->FR & RXFE) == 0) {
  while(uart->FR & TXFF);
  char ch = upperchar(uart->DR);
  if(ch == 'N'){
    print_uart0("N was pressed\n");
    Input = 2;
  }
  else if(ch == 'E'){
    print_uart0("E was pressed\n");
    Input = 1;
  }
  else if(ch == 'B'){
    print_uart0("Both buttons were pressed\n");
    Input = 3;
  }
  else{
    print_uart0("A different key was pressed\n");
    Input = 0;
  }
 }
 S = FSM[S].Next[Input];
}
 
void c_entry() {
 S = goN;
 for(;;) {
  int lightVal = FSM[S].Out;
  uart_echo(UART0);
  //uart_echo(UART1);
  //uart_echo(UART2);
 }
}
