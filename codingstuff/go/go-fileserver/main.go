package main

import (
	"fmt"
	"log"
	"net"
	"os/user"
	"strings"

	"github.com/gin-gonic/gin"
)

func main() {
	addrs, err := net.InterfaceAddrs()
	address := addrs[1].String()
	ip4 := strings.Split(address, "/")[0]
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("address: ", ip4)
	router := gin.Default()
	usr, err := user.Current()
	if err != nil {
		log.Fatal(err)
	}
	dst := usr.HomeDir

	router.MaxMultipartMemory = 8 << 20
	router.POST("/upload", func(c *gin.Context) {
		file, _ := c.FormFile("file")
		log.Println(file.Filename)
		c.SaveUploadedFile(file, dst)
	})

}
