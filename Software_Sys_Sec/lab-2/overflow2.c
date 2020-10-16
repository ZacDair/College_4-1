#include <stdio.h>
#include <string.h>

void overflowtest(){
	printf("%s\n", "Execution Hijacked!");
}

void main(int argc, char *argv[]){
	char buffer[10];
	strcpy(buffer, argv[1]);
	printf("%s\n", "Executed Normally");
}
