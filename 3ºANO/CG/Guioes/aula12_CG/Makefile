devil_cflags := $(shell pkg-config --cflags IL)
devil_libs := $(shell pkg-config --libs IL)

CXXFLAGS := -O0 -g -Wall -Wextra $(devil_cflags)
LIBS := $(devil_libs)

main: main.cpp
	g++ main.cpp -o main -lGL -lGLU -lglut -lGLEW $(LIBS)
run:
	./main
