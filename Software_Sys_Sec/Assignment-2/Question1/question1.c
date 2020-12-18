#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>
#include <sys/types.h>

struct politician {
	int id;
	int votes;
	char *name;
};

void cheater(){
	printf("Election Rigged!!\n");
}

int main(int argc, char **argv){
	struct politician *p1, *p2;

	p1 = malloc(sizeof(struct politician));
	p1->id = 0;
	p1->votes = 16000;
	p1->name = malloc(36);

	p2 = malloc(sizeof(struct politician));
	p2->id = 1;
	p2->votes = 28000;
	p2->name = malloc(36);

	strcpy(p1->name, argv[1]);
	strcpy(p2->name, argv[2]);

	printf("Election results calculated!\n");
}

