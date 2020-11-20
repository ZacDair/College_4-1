#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

void partialwin(){
	printf("Achieved 1/2!\n");
}

void fullwin(){
	printf("Achieved 2/2!\n");
}

void vuln(){
	char buffer[36];
	
	gets(buffer);
	printf("Buffer contents %s\n", buffer);
}

int main(int argc, char **argv){
	vuln();
}
