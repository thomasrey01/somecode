#include <stdio.h>

void test(int arr[])
{
    arr[0] = 1;
}

int main()
{
    int arr[2];
    arr[0] = 2;
    arr[1] = 1;
    test(arr);
    printf("%d\n", arr[0]);
}
