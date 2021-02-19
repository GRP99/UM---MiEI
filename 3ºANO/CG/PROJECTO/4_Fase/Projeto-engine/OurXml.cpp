#define _CRT_SECURE_NO_WARNINGS

#include "tinyxml2.h"
#include "Our3d.h"

int indice_luz = 0;
int tamanho_luz = 0;
luz *l;

void parsePontos(tinyxml2::XMLElement *ele, operacao *o) { // ele == fst ponto
    int r = 0;
    o->tempoAcumulador = 0;
    o->estatico = False;

    if (ele != NULL) {
        int size_pontos = 1;
        o->pontos = (float **) malloc(sizeof(float *) * size_pontos);
        o->a = 0;

        while (ele != NULL) {
            if (r >= size_pontos) {
                size_pontos *= 2;
                o->pontos = (float **) realloc(o->pontos, size_pontos * sizeof(float *));
            }

            if (std::strcmp("point", ele->Name()) == 0) {
                const char *x = ele->Attribute("X");
                const char *y = ele->Attribute("Y");
                const char *z = ele->Attribute("Z");

                o->pontos[r] = (float *) malloc(sizeof(float) * 3);
                if (x != NULL) o->pontos[r][0] = atof(x); else o->pontos[r][0] = 0;
                if (y != NULL) o->pontos[r][1] = atof(y); else o->pontos[r][1] = 0;
                if (z != NULL) o->pontos[r][2] = atof(z); else o->pontos[r][2] = 0;
                r++;
            }
            ele = ele->NextSiblingElement();
        }
        o->numero_pontos = r;
        if (o->numero_pontos < 4) {
            o->numero_pontos = 0;
            free(o->pontos);
        }
    }
}


int parseModels(tinyxml2::XMLElement *ele, char ***f, char ***textures, material_cor **mat) { // ele == fst model
    int r = 0;
    if (ele != NULL) {
        int size_xml = 1;
        char **fichs = (char **) malloc(sizeof(char *) * size_xml);
        char **t = (char **) malloc(sizeof(char *) * size_xml);
        material_cor *m = (material_cor *) malloc(sizeof(char *) * size_xml);

        while (ele != NULL) {
            FILE *fp = fopen(ele->Attribute("file"), "r");
            if (fp != NULL) {
                if (r >= size_xml) {
                    size_xml *= 2;
                    fichs = (char **) realloc(fichs, size_xml * sizeof(char *));
                    t = (char **) realloc(t, size_xml * sizeof(char *));
                    m = (material_cor *) realloc(m, sizeof(material_cor) * size_xml);
                }
                if (ele->Attribute("texture") != NULL) {
                    FILE *fp2 = fopen(ele->Attribute("texture"), "r");
                    if (fp2 != NULL) {
                        t[r] = strdup(ele->Attribute("texture"));
                        printf(" Leu textura |%s|", t[r]);
                    }
                } else {
                    t[r] = strdup("/home/goncalo/Documentos/3ANO/CG/PROJECTO/4_Fase/CG_testes/textures/white.png");
                }

                material_cor aux;
                aux.dif_spec_emi_amb[0] = false;
                aux.dif_spec_emi_amb[1] = false;
                aux.dif_spec_emi_amb[2] = false;
                aux.dif_spec_emi_amb[3] = false;
                aux.dif[0] = 0;
                aux.dif[1] = 0;
                aux.dif[2] = 0;
                aux.dif[3] = 1;
                aux.spec[0] = 0;
                aux.spec[1] = 0;
                aux.spec[2] = 0;
                aux.spec[3] = 1;
                aux.emi[0] = 0;
                aux.emi[1] = 0;
                aux.emi[2] = 0;
                aux.emi[3] = 1;
                aux.amb[0] = 0;
                aux.amb[1] = 0;
                aux.amb[2] = 0;
                aux.amb[3] = 1;

                const char *diffr = ele->Attribute("diffR");
                const char *diffg = ele->Attribute("diffG");
                const char *diffb = ele->Attribute("diffB");

                const char *specr = ele->Attribute("specR");
                const char *specg = ele->Attribute("specG");
                const char *specb = ele->Attribute("specB");

                const char *emir = ele->Attribute("emiR");
                const char *emig = ele->Attribute("emiG");
                const char *emib = ele->Attribute("emiB");

                const char *ambr = ele->Attribute("ambR");
                const char *ambg = ele->Attribute("ambG");
                const char *ambb = ele->Attribute("ambB");

                if (diffr != NULL) {
                    aux.dif[0] = atof(diffr);
                    aux.dif_spec_emi_amb[0] = true;
                }
                if (diffg != NULL) {
                    aux.dif[1] = atof(diffg);
                    aux.dif_spec_emi_amb[0] = true;
                }
                if (diffb != NULL) {
                    aux.dif[2] = atof(diffb);
                    aux.dif_spec_emi_amb[0] = true;
                }

                if (specr != NULL) {
                    aux.spec[0] = atof(specr);
                    aux.dif_spec_emi_amb[1] = true;
                }
                if (specg != NULL) {
                    aux.spec[1] = atof(specg);
                    aux.dif_spec_emi_amb[1] = true;
                }
                if (specb != NULL) {
                    aux.spec[2] = atof(specb);
                    aux.dif_spec_emi_amb[1] = true;
                }

                if (emir != NULL) {
                    aux.emi[0] = atof(emir);
                    aux.dif_spec_emi_amb[2] = true;
                }
                if (emig != NULL) {
                    aux.emi[1] = atof(emig);
                    aux.dif_spec_emi_amb[2] = true;
                }
                if (emib != NULL) {
                    aux.emi[2] = atof(emib);
                    aux.dif_spec_emi_amb[2] = true;
                }

                if (ambr != NULL) {
                    aux.amb[0] = atof(ambr);
                    aux.dif_spec_emi_amb[3] = true;
                }
                if (ambg != NULL) {
                    aux.amb[1] = atof(ambg);
                    aux.dif_spec_emi_amb[3] = true;
                }
                if (ambb != NULL) {
                    aux.amb[2] = atof(ambb);
                    aux.dif_spec_emi_amb[3] = true;
                }

                m[r] = aux;
                fichs[r] = strdup(ele->Attribute("file"));
                fclose(fp);
                r++;
            }
            ele = ele->NextSiblingElement();
        }
        if (r == 0) {
            //free(fichs);free(t);free(m);
        } else {
            *f = fichs;
            *textures = t;
            *mat = m;
        }
    }
    return r;
}


void parseLight(tinyxml2::XMLElement *ele) { // ele == fst luz
    if (ele != NULL) {
        if (tamanho_luz == 0) {
            tamanho_luz = 1;
            l = (luz *) malloc(sizeof(luz) * tamanho_luz);
        }
        while (ele != NULL) {
            if (indice_luz < 8) {
                if (indice_luz >= tamanho_luz) {
                    tamanho_luz *= 2;
                    l = (luz *) realloc(l, tamanho_luz * sizeof(luz));
                }
                if (strcmp(ele->Attribute("type"), "DIRECTIONAL") == 0) {
                    luz o;
                    o.tipo = Directional;
                    const char *x = ele->Attribute("posX");
                    const char *y = ele->Attribute("posY");
                    const char *z = ele->Attribute("posZ");

                    if (x != NULL) o.pos[0] = -atof(x); else o.pos[0] = 0;
                    if (y != NULL) o.pos[1] = -atof(y); else o.pos[1] = 0;
                    if (z != NULL) o.pos[2] = -atof(z); else o.pos[2] = 0;
                    o.pos[3] = 0.0f;

                    l[indice_luz] = o;
                    indice_luz++;

                } else if (strcmp(ele->Attribute("type"), "POINT") == 0) {
                    luz o;
                    o.tipo = Point;
                    const char *x = ele->Attribute("posX");
                    const char *y = ele->Attribute("posY");
                    const char *z = ele->Attribute("posZ");
                    const char *a = ele->Attribute("att");

                    if (x != NULL) o.pos[0] = atof(x); else o.pos[0] = 0;
                    if (y != NULL) o.pos[1] = atof(y); else o.pos[1] = 0;
                    if (z != NULL) o.pos[2] = atof(z); else o.pos[2] = 0;
                    if (a != NULL) o.atenuacao = atof(a); else o.atenuacao = 0;
                    o.pos[3] = 1.0f;

                    l[indice_luz] = o;
                    indice_luz++;

                } else if (strcmp(ele->Attribute("type"), "SPOT") == 0) {
                    luz o;
                    o.tipo = Spot;

                    const char *x = ele->Attribute("posX");
                    const char *y = ele->Attribute("posY");
                    const char *z = ele->Attribute("posZ");

                    const char *a = ele->Attribute("att");
                    const char *cut = ele->Attribute("cut");
                    const char *expo = ele->Attribute("exp");

                    const char *dirx = ele->Attribute("dirX");
                    const char *diry = ele->Attribute("dirY");
                    const char *dirz = ele->Attribute("dirZ");

                    if (x != NULL) o.pos[0] = atof(x); else o.pos[0] = 0;
                    if (y != NULL) o.pos[1] = atof(y); else o.pos[1] = 0;
                    if (z != NULL) o.pos[2] = atof(z); else o.pos[2] = 0;
                    o.pos[3] = 1.0f;

                    if (a != NULL) o.atenuacao = atof(a); else o.atenuacao = 0;
                    if (cut != NULL && ((atof(cut) >= 0 && atof(cut) <= 90) || (atof(cut) == 180)))
                        o.cutoff = atof(cut);
                    else o.cutoff = 45;
                    if (expo != NULL && (atof(expo) >= 0 && atof(expo) <= 128))
                        o.exponent = atof(expo);
                    else o.exponent = 0;

                    if (dirx != NULL) o.direcao[0] = atof(dirx); else o.direcao[0] = 0;
                    if (diry != NULL) o.direcao[1] = atof(diry); else o.direcao[1] = 0;
                    if (dirz != NULL) o.direcao[2] = atof(dirz); else o.direcao[2] = 0;

                    l[indice_luz] = o;
                    indice_luz++;
                }
            }
            ele = ele->NextSiblingElement();
        }
    }
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
    aux.path_texturas = NULL,
            aux.numero_ficheiros = 0;
    //grupo * inside;
    aux.inside = NULL;
    aux.size_filhos = 0;
    aux.filhos = 0;
    aux.cor = NULL;

    if (pai != NULL) {
        ele = pai->FirstChildElement();
    }
    if (ele != NULL) { // tem dentro algo
        while (ele != NULL) {
            if (std::strcmp("translate", ele->Name()) == 0) {
                operacao o;
                o.time = 0;
                o.tempoAnterior = 0;
                o.numero_pontos = 0;
                o.estatico = True;
                o.pontos = NULL;
                o.antYY[0] = 0;
                o.antYY[1] = 1;
                o.antYY[2] = 0;
                o.id_operacao = Translacao;

                const char *x = ele->Attribute("X");
                const char *y = ele->Attribute("Y");
                const char *z = ele->Attribute("Z");
                const char *t = ele->Attribute("time");

                if (x != NULL) o.x = atof(x); else o.x = 0;
                if (y != NULL) o.y = atof(y); else o.y = 0;
                if (z != NULL) o.z = atof(z); else o.z = 0;
                if (t != NULL) {
                    o.time = atof(t);
                    o.estatico = False;
                    parsePontos(ele->FirstChildElement(), &o);
                } else o.time = 0;

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
                o.time = 0;
                o.tempoAnterior = 0;
                o.numero_pontos = 0;
                o.estatico = True;
                o.pontos = NULL;
                o.antYY[0] = 0;
                o.antYY[1] = 1;
                o.antYY[2] = 0;

                const char *x = ele->Attribute("axisX");
                const char *y = ele->Attribute("axisY");
                const char *z = ele->Attribute("axisZ");
                const char *a = ele->Attribute("angle");
                const char *t = ele->Attribute("time");

                if (x != NULL) o.x = atof(x); else o.x = 0;
                if (y != NULL) o.y = atof(y); else o.y = 0;
                if (z != NULL) o.z = atof(z); else o.z = 0;
                if (a != NULL) o.a = atof(a);
                else {
                    o.a = 0;
                    if (t != NULL) o.time = atof(t); else o.time = 0;
                    o.estatico = false;
                }

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
                o.time = 0;
                o.tempoAnterior = 0;
                o.numero_pontos = 0;
                o.estatico = True;
                o.pontos = NULL;
                o.antYY[0] = 0;
                o.antYY[1] = 1;
                o.antYY[2] = 0;

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
                aux.numero_ficheiros = parseModels(ele->FirstChildElement(), &(aux.ficheiros), &(aux.path_texturas),
                                                   &(aux.mats));
                aux.figs = (figura *) malloc(sizeof(figura) * aux.numero_ficheiros);
                aux.texturas = (unsigned int *) malloc(sizeof(unsigned int) * aux.numero_ficheiros);
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

        while (ele != NULL && std::strcmp("scene", ele->Name()) != 0) {
            ele = doc.NextSiblingElement();
        }

        if (ele != NULL) { // ele = scene
            ele = ele->FirstChildElement();
            if (ele != NULL) { // ele = 1º group
                grupo *gr;
                gr = (grupo *) malloc(sizeof(grupo));
                int size_grupo = 1;
                int usado = 0;

                while (ele != NULL &&
                       (std::strcmp("group", ele->Name()) == 0 || std::strcmp("lights", ele->Name()) == 0)) {
                    if (std::strcmp("group", ele->Name()) == 0) {
                        if (usado >= size_grupo) {
                            size_grupo *= 2;
                            gr = (grupo *) realloc(gr, size_grupo * sizeof(grupo));
                        }
                        parseGrupo(ele, gr + usado);
                        usado++;
                    } else if (std::strcmp("lights", ele->Name()) == 0) {
                        parseLight(ele->FirstChildElement());
                    }
                    ele = ele->NextSiblingElement();
                }
                *g = gr;
                r = usado;
            }
        }
    }
    return r;
}

int exportLight(luz **ls) {
    *ls = l;
    return indice_luz;
}