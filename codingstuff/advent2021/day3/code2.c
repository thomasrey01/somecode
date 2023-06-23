#include <stdio.h>
#include <stdlib.h>

#define DEPTH 5

typedef struct TreeNode *Tree;

struct TreeNode 
{
    int lCount, rCount;
    Tree lChild, rChild;
};

// Tree emptyTree() {
//     return NULL;
// }

// Tree newTree(int lC, int rC, Tree tL, Tree tR) {
//     Tree new = malloc(sizeof(struct TreeNode));
//     assert(new != NULL);
//     new->lCount = lC;
//     new->rCount = rC;
//     new->lChild = tL;
//     new->rChild = tR;
// }

Tree makeTree(int depth) 
{
    if (depth == 0) {
        return NULL;
    }
    Tree new = malloc(sizeof(struct TreeNode));
    new->rCount = 0;
    new->lCount = 0;
    new->rChild = makeTree(depth-1);
    new->lChild = makeTree(depth-1);
    return new;
}

void freeTree(Tree t) 
{
    if (t == NULL) {
        return;
    }
    freeTree(t->lChild);
    freeTree(t->rChild);
    free(t);
} 

void addToTree(char in[], Tree t)
{
    Tree tPtr;
    tPtr = t;
    for (int i = 0; i < DEPTH; i++) {
        if (in[i] == '1') {
            tPtr->rCount++;
            tPtr = tPtr->rChild;
        } else if (in[i] == '0') {
            tPtr->lCount++;
            tPtr = tPtr->lChild;
        }
    }
}

int getOxygen(Tree t)
{
    int num = 0;
    Tree t1 = t;
    for (int i = 0; i < DEPTH; i++) {
        if (t1->rCount >= t1->lCount) {
            num += 1 << (DEPTH - 1 - i);
            t1 = t1->rChild;
        } else {
            t1 = t1->lChild;
        }
    }
    return num;
}

int getCO2(Tree t)
{
    int num = 0;
    Tree t1 = t;
    for (int i = 0; i < DEPTH; i++) {
        if (t1->lCount <= t1->rCount) {
            printf("1");
            num += 1 << (DEPTH - 1 - i);
            t1 = t1->lChild;
        } else {
            printf("0");
            
            t1 = t1->rChild;
        }
    }
    return num;
}

int main() 
{
    // int 
    Tree t = makeTree(DEPTH);
    char in[13];
    char c;
    for (int i = 0; i < 12; i++) {
        for (int j = 0; j < DEPTH; j++) {
            scanf("%c", &c);
            in[j] = c;
        }
        scanf("\n");
        addToTree(in, t);
    }
    int ox = getOxygen(t);
    int co = getCO2(t);
    printf("oxygen: %d\n", ox);
    printf("co2: %d\n", co);
    printf("%d\n", ox * co);
    freeTree(t);
    return 0;
}