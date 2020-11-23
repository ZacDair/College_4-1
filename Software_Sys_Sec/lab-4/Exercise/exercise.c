#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

int target;

void vuln(char *string){
	printf(string);
	if(target == 255){
		printf("you have modified the target :)\n");
	}
	else{
		printf("target is %d: (\n", target);
	}
}

int main(int argc, char **argv){
	vuln(argv[1]);
}
