import xml.etree.ElementTree as ET
import codecs


def main() -> ():
    tree = ET.parse('jcrpubs.xml')

    root = tree.getroot()

    for child in root:
        dict_pub = {}
        list_author_ref = []
        list_authors = []
        list_editor_ref = []
        list_editors = []
        list_deliverables = []
        type_pub = child.tag.capitalize()
        id_pub = child.attrib.get('id')

        for x in range(len(child)):
            if child[x].tag == "author-ref":
                list_author_ref.append(child[x].attrib.get('authorid'))
                dict_pub[child[x].tag] = list_author_ref
            elif child[x].tag == "editor-ref":
                list_editor_ref.append(child[x].attrib.get('authorid'))
                dict_pub[child[x].tag] = list_editor_ref
            elif child[x].tag == "deliverables":
                for deliverable in child[x]:
                    list_deliverables.append(deliverable.attrib.get('url'))
                    dict_pub[child[x].tag] = list_deliverables
            else:
                if child[x].tag == "author":
                    author = {"id": child[x].attrib.get('id'), "nome": child[x].text}
                    list_authors.append(author)
                    dict_pub[child[x].tag] = list_authors

                    list_author_ref.append(child[x].attrib.get('id'))
                    dict_pub["author-ref"] = list_author_ref
                elif child[x].tag == "editor":
                    editor = {"id": child[x].attrib.get('id'), "nome": child[x].text}
                    list_editors.append(editor)
                    dict_pub[child[x].tag] = list_editors

                    list_editor_ref.append(child[x].attrib.get('id'))
                    dict_pub["editor-ref"] = list_editor_ref
                else:
                    dict_pub[child[x].tag] = child[x].text

        with codecs.open('pubs.txt', 'a', "utf-8") as file:
            line = "###  http://www.di.uminho.pt/prc2021/A83732-TP5#" + id_pub + "\n:" + id_pub + " rdf:type owl:NamedIndividual ,\n                    :" + type_pub + " ;\n"
            file.write(line)

            number_line = 0
            for key in dict_pub:
                if key == "author-ref":
                    flag = True
                    for ref_author in dict_pub.get(key):
                        if flag:
                            if number_line > 0:
                                line = " ;\n           :wasWritten :" + ref_author
                                file.write(line)
                                flag = False
                                number_line = number_line + 1
                            else:
                                line = "           :wasWritten :" + ref_author
                                file.write(line)
                                flag = False
                                number_line = number_line + 1
                        else:
                            line = " ,\n                       :" + ref_author
                            file.write(line)

                elif key == "editor-ref":
                    flag = True
                    for ref_editor in dict_pub.get(key):
                        if flag:
                            if number_line > 0:
                                line = " ;\n           :wasEdit :" + ref_editor
                                file.write(line)
                                flag = False
                                number_line = number_line + 1
                            else:
                                line = "           :wasEdit :" + ref_editor
                                file.write(line)
                                flag = False
                                number_line = number_line + 1
                        else:
                            line = " ,\n                       :" + ref_editor
                            file.write(line)

                elif key == "deliverables":
                    flag = True
                    for deliverable in dict_pub.get(key):
                        if flag:
                            if number_line > 0:
                                line = "; \n           :have :pdf" + id_pub
                                file.write(line)
                                flag = False
                                number_line = number_line + 1
                            else:
                                line = "\n           :have :pdf" + id_pub
                                file.write(line)
                                flag = False
                                number_line = number_line + 1
                        else:
                            line = " ,\n                       :pdf" + id_pub
                            number_line = number_line + 1
                            file.write(line)

                elif key != "author" and key != "editor":
                    if number_line == 0:
                        line = "           :" + key + " \"" + dict_pub.get(key).strip() + "\"^^xsd:string"
                        number_line = number_line + 1
                        file.write(line)
                    else:
                        line = " ;\n           :" + key + " \"" + dict_pub.get(key).strip() + "\"^^xsd:string"
                        number_line = number_line + 1
                        file.write(line)

            file.write(".\n\n")

            for key in dict_pub:
                if key == "deliverables":
                    for deliverable in dict_pub.get(key):
                        line1 = "###  http://www.di.uminho.pt/prc2021/A83732-TP5#pdf" + id_pub
                        line2 = "\n:pdf" + id_pub + " rdf:type owl:NamedIndividual ,\n              :PDF ;"
                        line3 = "\n     :url \"" + deliverable + "\"^^xsd:string .\n\n"
                        file.write(line1 + line2 + line3)
                if key == "author":
                    for author in dict_pub.get(key):
                        line1 = "###  http://www.di.uminho.pt/prc2021/A83732-TP5#" + author.get("id")
                        line2 = "\n:" + author.get("id") + " rdf:type owl:NamedIndividual ,\n              :Author ;"
                        line3 = "\n     :nome \"" + author.get("nome") + "\"^^xsd:string .\n\n"
                        file.write(line1 + line2 + line3)
                if key == "editor":
                    for editor in dict_pub.get(key):
                        line1 = "###  http://www.di.uminho.pt/prc2021/A83732-TP5#" + editor.get("id")
                        line2 = "\n:" + editor.get("id") + " rdf:type owl:NamedIndividual ,\n              :Author ;"
                        line3 = "\n     :nome \"" + editor.get("nome") + "\"^^xsd:string .\n\n"
                        file.write(line1 + line2 + line3)


main()
