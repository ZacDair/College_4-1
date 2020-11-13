#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

void vuln(){
	char buffer[64];
	
	printf("provide input please: "); fflush(stdout);
	
	gets(buffer);
}

int main(int argc, char **argv){
	
	vuln();
	
}
