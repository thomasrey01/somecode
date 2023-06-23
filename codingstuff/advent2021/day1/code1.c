#include <stdio.h>
#include <stdlib.h>

int main() 
{
    int oldNum;
    int num;
    int cnt = 0;
    scanf("%d\n", &oldNum);    
    for (int i = 1; i < 2000; i++) {
        scanf("%d\n", &num);
        if (num > oldNum) {
            cnt++;
        }
        oldNum = num;
    }
    printf("%d\n", cnt);
}