devil_cflags := $(shell pkg-config --cflags IL)
devil_libs := $(shell pkg-config --libs IL)

CXXFLAGS := -O0 -g -Wall -Wextra $(devil_cflags)
LIBS := $(devil_libs)


main: main.cpp Our3d.cpp OurXml.cpp tinyxml2.cpp
	g++ main.cpp Our3d.cpp OurXml.cpp tinyxml2.cpp CatmullRom.cpp -o main -lGL -lGLU -lglut -lGLEW  $(LIBS)


cone:
	./main "/home/goncalo/Documentos/3ANO/CG/PROJECTO/4_Fase/CG_testes/xml/cone.xml"

caixa:
	./main "/home/goncalo/Documentos/3ANO/CG/PROJECTO/4_Fase/CG_testes/xml/caixa.xml"

esfera:
	./main "/home/goncalo/Documentos/3ANO/CG/PROJECTO/4_Fase/CG_testes/xml/esfera.xml"

plano:
	./main "/home/goncalo/Documentos/3ANO/CG/PROJECTO/4_Fase/CG_testes/xml/plano.xml"

tea: 
	./main "/home/goncalo/Documentos/3ANO/CG/PROJECTO/4_Fase/CG_testes/xml/tea.xml"


luz_spot:
	./main "/home/goncalo/Documentos/3ANO/CG/PROJECTO/4_Fase/CG_testes/xml/luz_spot.xml"

luz_dir: 
	./main "/home/goncalo/Documentos/3ANO/CG/PROJECTO/4_Fase/CG_testes/xml/luz_direcional.xml"

terra:
	./main "/home/goncalo/Documentos/3ANO/CG/PROJECTO/4_Fase/CG_testes/xml/terra.xml"


solar:
	./main "/home/goncalo/Documentos/3ANO/CG/PROJECTO/4_Fase/CG_testes/xml/SistemaSolar.xml"
