#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) 
{
    int count[12] = {0,0,0,0,0,0,0,0,0,0,0,0};
    for (int i = 0; i < 1000; i++) {
        for (int j = 0; j < 12; j++) {
            char c;
            scanf("%c", &c);
            if (c == '1') {
                count[j]++;
            } else {
                count[j]--;
            }
            scanf("\n");
        }
    }
    int num = 0;
    int otherNum = 0;
    for (int i = 0; i < 12; i++) {
        if (count[i] > 0) {
            num += 1 << (11 - i);
        } else {
            otherNum += 1 << (11 - i);
        }
    }
    printf("%d\n", num * otherNum);
    
}