#include <stdio.h>
#include <stdlib.h>

int main()
{
    int cnt = 0;
    int newCnt;
    int prevCnt;
    int firstNum;
    int secondNum;
    int thirdNum;
    scanf("%d\n", &firstNum);
    scanf("%d\n", &secondNum);
    scanf("%d\n", &thirdNum);
    prevCnt = firstNum + secondNum + thirdNum;
    for (int i = 3; i < 2000; i++) {
        newCnt = prevCnt - firstNum;
        firstNum = secondNum;
        secondNum = thirdNum;
        scanf("%d\n", &thirdNum);
        newCnt += thirdNum;
        if (newCnt > prevCnt) cnt++;
        prevCnt = newCnt;
    }
    printf("%d\n", cnt);
}