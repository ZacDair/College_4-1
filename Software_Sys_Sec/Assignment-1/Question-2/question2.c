#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

int grade;

void securegrading(){
	if(grade < 40){
		printf("Usual grade attained.\n");
	}
	else if(grade < 100){
		printf("Excellent grade attained!\n");
	}
	else if (grade == 100){
		printf("Perfect grade attained!\n");
	}
	exit(1);
}

int main(int argc, char **argv){
	char input[48];

	grade = 10;

	gets(input);
	printf("User input:");
	printf(input);
}
