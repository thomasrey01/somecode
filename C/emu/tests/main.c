#include <stdio.h>
#include <stdlib.h>
#include "disassembler.h"

int main (int argc, char **argv)
{
	FILE *f = fopen(argv[1], "rb");
	if (f == NULL) {
		printf("error: Couldn't open %s\n", argv[1]);
		exit(1);
	}

	// Get the file size and read it into a memory buffer
	fseek(f, 0L, SEEK_END);
	int fsize = ftell(f);
	fseek(f, 0L, SEEK_SET);

	unsigned char *buffer = malloc(fsize);

	fread(buffer, fsize, 1, f);
	fclose(f);

	int pc = 0;

	while (pc < fsize) {
		pc += disassemble8080op(buffer, pc);
	}
	return 0;
}
