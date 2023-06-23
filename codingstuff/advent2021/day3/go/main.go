package main

import (
	"fmt"
	"io"
	"io/ioutil"
	"os"
)

var DEPTH int = 12

type BinaryNode struct {
	left   *BinaryNode
	right  *BinaryNode
	lCount int64
	rCount int64
}

type BinaryTree struct {
	root *BinaryNode
}

func makeNDepthTree(depth int) *BinaryTree {
	t := &BinaryTree{}
	t.root = makeTreeRec(depth)
	return t
}

func makeTreeRec(depth int) *BinaryNode {
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
		fmt.Fprint(w, "|")
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

func findOxygen(tree *BinaryTree) int {
	t := tree.root
	val := 0
	for i := 0; t != nil; i++ {
		if t.lCount > t.rCount {
			t = t.left
		} else {
			val += (1 << (DEPTH - 1 - i))
			t = t.right
		}
	}
	return val
}

func findCO2(tree *BinaryTree) int {
	t := tree.root
	val := 0
	for i := 0; t != nil; i++ {
		if (t.lCount <= t.rCount && t.lCount != 0) || t.rCount == 0 {
			t = t.left
		} else {
			val += (1 << (DEPTH - 1 - i))
			t = t.right
		}
	}
	return val
}

func main() {
	tree := makeNDepthTree(DEPTH)
	if len(os.Args) < 2 {
		fmt.Println("Missing parameter, provile file name")
		return
	}
	data, err := ioutil.ReadFile(os.Args[1])
	if err != nil {
		fmt.Println("Can't read file: ", os.Args[1])
		panic(err)
	}
	fillTree(tree, data)
	co2 := findCO2(tree)
	ox := findOxygen(tree)
	fmt.Println("CO2: ", co2)
	fmt.Println("Oxygen: ", ox)
	fmt.Println("Result: ", co2*ox)
}
