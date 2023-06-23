#include <stdio.h>
#include <stdlib.h>

int glbinit = 5;
int glb;

int main() {
	int *allocated = malloc(sizeof(int));
	int *local;
	printf("allocated: %x\n", allocated);
	printf("global: %x\n", &glb);
	printf("global init: %x\n", &glbinit);
	printf("local: %x\n", local);
	free(allocated);
}