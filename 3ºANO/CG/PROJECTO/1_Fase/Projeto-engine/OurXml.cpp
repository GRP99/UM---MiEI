#include "tinyxml2.h"


int abrir_xml_file(char *xml, char ***ficheiros) {
    int r = 0;
    tinyxml2::XMLDocument doc;
    tinyxml2::XMLError er = doc.LoadFile(xml);
    char **fichs;

    if (er == tinyxml2::XML_SUCCESS) {
        tinyxml2::XMLElement *ele = doc.RootElement();
        while (ele != NULL && std::strcmp("scene", ele->Name()) != 0) {
            ele = doc.NextSiblingElement();
        }

        if (ele != NULL) {
            ele = ele->FirstChildElement();
        }

        int size_xml = 1;

        fichs = (char **) malloc(sizeof(char *) * size_xml);

        while (ele != NULL) {
            printf("XML:: Ficheiro: %s", ele->Attribute("file"));
            FILE *fp = fopen(ele->Attribute("file"), "r");
            if (fp != NULL) {
                if (r >= size_xml) {
                    size_xml *= 2;
                    fichs = (char **) realloc(fichs, size_xml);
                }
                fichs[r] = strdup(ele->Attribute("file"));
                r++;
                fclose(fp);
            }
            ele = ele->NextSiblingElement();
        }
    }
    if (r == 0) {
        free(fichs);
    } else {
        *ficheiros = fichs;
    }
    return r;
}