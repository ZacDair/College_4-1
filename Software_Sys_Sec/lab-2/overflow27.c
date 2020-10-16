#include <stdio.h>
#include <string.h>

void overflowtest(){
	printf("%s\n", "Execution Hijacked!");
}

void func(char *str){
	char buffer[27];
	strcpy(buffer, str);
}

void main(int argc, char *argv[]){
	func(argv[1]);
	printf("%s\n", "Executed Normally");
}
