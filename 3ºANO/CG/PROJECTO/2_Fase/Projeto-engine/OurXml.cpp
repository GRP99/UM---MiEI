#include "tinyxml2.h"
#include "Our3d.h"

int parseModels(tinyxml2::XMLElement *ele, char ***f) { // ele == fst model
    int r = 0;
    if (ele != NULL) {
        int size_xml = 1;
        char **fichs = (char **) malloc(sizeof(char *) * size_xml);

        while (ele != NULL) {
            FILE *fp = fopen(ele->Attribute("file"), "r");

            if (fp != NULL) {
                if (r >= size_xml) {
                    size_xml *= 2;
                    fichs = (char **) realloc(fichs, size_xml * sizeof(char *));
                }

                fichs[r] = strdup(ele->Attribute("file"));
                r++;

                fclose(fp);
            }
            ele = ele->NextSiblingElement();
        }

        if (r == 0) free(fichs); else *f = fichs;
    }
    return r;
}


void parseGrupo(tinyxml2::XMLElement *pai, grupo *g) {  // pai = <group>
    tinyxml2::XMLElement *ele = NULL;

    grupo aux;
    aux.numero_op = 0;
    aux.ops = (operacao *) malloc(sizeof(operacao) * 1); // falta  realocar se houver mais
    int size_ops = 1;
    aux.figs = NULL;
    //char *ficheiros;
    aux.ficheiros = NULL;
    aux.numero_ficheiros = 0;
    //grupo * inside;
    aux.inside = NULL;
    aux.size_filhos = 0;
    aux.filhos = 0;
    aux.cor = NULL;

    if (pai != NULL) ele = pai->FirstChildElement();

    if (ele != NULL) { // tem dentro algo
        while (ele != NULL) {
            if (std::strcmp("translate", ele->Name()) == 0) {
                operacao o;
                o.id_operacao = Translacao;
                const char *x = ele->Attribute("X");
                const char *y = ele->Attribute("Y");
                const char *z = ele->Attribute("Z");
                if (x != NULL) o.x = atof(x); else o.x = 0;
                if (y != NULL) o.y = atof(y); else o.y = 0;
                if (z != NULL) o.z = atof(z); else o.z = 0;
                o.a = 0;
                //Add
                if (aux.numero_op >= size_ops) {
                    size_ops *= 2;
                    aux.ops = (operacao *) realloc(aux.ops, size_ops * sizeof(operacao));
                }
                aux.ops[aux.numero_op] = o;
                aux.numero_op++;

            } else if (std::strcmp("rotate", ele->Name()) == 0) {
                operacao o;
                o.id_operacao = Rotacao;
                const char *x = ele->Attribute("axisX");
                const char *y = ele->Attribute("axisY");
                const char *z = ele->Attribute("axisZ");
                const char *a = ele->Attribute("angle");
                if (x != NULL) o.x = atof(x); else o.x = 0;
                if (y != NULL) o.y = atof(y); else o.y = 0;
                if (z != NULL) o.z = atof(z); else o.z = 0;
                if (a != NULL) o.a = atof(a); else o.a = 0;
                //Add
                if (aux.numero_op >= size_ops) {
                    size_ops *= 2;
                    aux.ops = (operacao *) realloc(aux.ops, size_ops * sizeof(operacao));
                }
                aux.ops[aux.numero_op] = o;
                aux.numero_op++;

            } else if (std::strcmp("scale", ele->Name()) == 0) {
                operacao o;
                o.id_operacao = Escala;
                const char *x = ele->Attribute("X");
                const char *y = ele->Attribute("Y");
                const char *z = ele->Attribute("Z");
                if (x != NULL) o.x = atof(x); else o.x = 1;
                if (y != NULL) o.y = atof(y); else o.y = 1;
                if (z != NULL) o.z = atof(z); else o.z = 1;
                o.a = 0;
                //Add
                if (aux.numero_op >= size_ops) {
                    size_ops *= 2;
                    aux.ops = (operacao *) realloc(aux.ops, size_ops * sizeof(operacao));
                }
                aux.ops[aux.numero_op] = o;
                aux.numero_op++;

            } else if (std::strcmp("models", ele->Name()) == 0) {
                aux.numero_ficheiros = parseModels(ele->FirstChildElement(), &(aux.ficheiros));
                aux.figs = (figura *) malloc(sizeof(figura) * aux.numero_ficheiros);
                //Add ao grupo

            } else if (std::strcmp("group", ele->Name()) == 0) {
                if (aux.size_filhos == 0) {
                    aux.inside = (grupo *) malloc(sizeof(grupo) * 1);
                    aux.size_filhos = 1;
                }
                if (aux.filhos >= aux.size_filhos) {
                    aux.size_filhos *= 2;
                    aux.inside = (grupo *) realloc(aux.inside, aux.size_filhos * sizeof(grupo));
                }
                // aux.inside[aux.filhos] =  (grupo ) malloc( sizeof(grupo));
                parseGrupo(ele, &(aux.inside[aux.filhos]));
                aux.filhos++;

            } else if (std::strcmp("color", ele->Name()) == 0) {
                color o;
                aux.cor = (color *) malloc(sizeof(color) * 1);
                const char *r = ele->Attribute("R");
                const char *g = ele->Attribute("G");
                const char *b = ele->Attribute("B");
                if (r != NULL) o.r = atoi(r); else o.r = 0;
                if (g != NULL) o.g = atoi(g); else o.g = 0;
                if (b != NULL) o.b = atoi(b); else o.b = 0;
                *(aux.cor) = o;
            }
            ele = ele->NextSiblingElement();
        }
        *g = aux;
    }
}


int abrir_xml_file(char *xml, grupo **g) {
    int r = 0;
    tinyxml2::XMLDocument doc;
    tinyxml2::XMLError er = doc.LoadFile(xml);
    char **fichs;

    if (er == tinyxml2::XML_SUCCESS) {
        tinyxml2::XMLElement *ele = doc.RootElement();

        while (ele != NULL && std::strcmp("scene", ele->Name()) != 0)
            ele = doc.NextSiblingElement();

        if (ele != NULL) { // ele = scene
            ele = ele->FirstChildElement();

            while (ele != NULL && std::strcmp("group", ele->Name()) != 0) {
                ele = doc.NextSiblingElement();
            }

            if (ele != NULL) { // ele = 1º group
                grupo *gr;
                gr = (grupo *) malloc(sizeof(grupo));
                int size_grupo = 1;
                int usado = 0;

                while (ele != NULL && std::strcmp("group", ele->Name()) == 0) {
                    if (usado >= size_grupo) {
                        size_grupo *= 2;
                        gr = (grupo *) realloc(gr, size_grupo * sizeof(grupo));
                    }
                    parseGrupo(ele, gr + usado);
                    usado++;
                    ele = ele->NextSiblingElement();
                }
                *g = gr;
                r = usado;
            }
        }
    }
    return r;
}