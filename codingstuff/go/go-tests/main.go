package main

import (
	"fmt"
	"io"
	"io/ioutil"
	"os"
)

type BinaryNode struct {
	left   *BinaryNode
	right  *BinaryNode
	lCount int64
	rCount int64
}

type BinaryTree struct {
	root *BinaryNode
}

func makeNDepthTree(depth int64) *BinaryTree {
	t := &BinaryTree{}
	t.root = makeTreeRec(depth)
	return t
}

func makeTreeRec(depth int64) *BinaryNode {
	if depth == 0 {
		return nil
	}
	t := &BinaryNode{lCount: 0, rCount: 0, left: makeTreeRec(depth - 1), right: makeTreeRec(depth - 1)}
	return t
}

func print(w io.Writer, node *BinaryNode, ns int, ch rune) {
	if node == nil {
		return
	}

	for i := 0; i < ns; i++ {
		fmt.Fprint(w, " ")
	}
	fmt.Fprintf(w, "%c : left:%v right:%v\n", ch, node.lCount, node.rCount)
	print(w, node.left, ns+2, 'L')
	print(w, node.right, ns+2, 'R')
}

func fillTree(tree *BinaryTree, data []byte) {
	t := tree.root
	for _, b := range data {
		if b == 10 {
			t = tree.root
		} else {
			if b == 48 {
				t.lCount++
				t = t.left
			} else if b == 49 {
				t.rCount++
				t = t.right
			}
		}
	}
}

func main() {

	tree := makeNDepthTree(4)
	if len(os.Args) < 2 {
		fmt.Println("Missing parameter, provile file name")
		return
	}
	data, err := ioutil.ReadFile(os.Args[1])
	if err != nil {
		fmt.Println("Can't read file:", os.Args[1])
		panic(err)
	}
	fillTree(tree, data)
	print(os.Stdout, tree.root, 0, 'M')
	// fmt.Println("First index of data: ", data[4])
}
