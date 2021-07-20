import os
import re
from jjcli import *

c = clfilter(opt="do:")


def create_tree(tree) -> list:
    # Second part of template must refer a tree structure
    if 'tree' in tree:
        list_paths = []

        last_dir_lvl = {0: './'}
        depth = 0

        lines = tree.split('\n')[1:]
        for line in [line for line in lines if line != '']:
            info = re.findall(r'(^[\-]*)\s*([^\n\/]+(/?))', line)
            level, content, directory = info[0]

            is_directory = True
            if directory == '':
                is_directory = False

            level = len(level)

            if level - depth == 0:
                list_paths.append(last_dir_lvl[depth] + content)

                if is_directory:
                    last_dir_lvl[depth + 1] = last_dir_lvl[depth] + content
                # clear levels
                else:
                    last_dir_lvl[depth + 1] = ''

            elif level - depth == 1:
                if level not in last_dir_lvl or last_dir_lvl[level] == '':
                    raise Exception('Ooops! Malformed directory tree !')

                else:
                    path = last_dir_lvl[level] + content
                    list_paths.append(path)

                    depth = level
                    if is_directory:
                        last_dir_lvl[depth + 1] = path

            elif depth > level:
                # clear levels
                for i in last_dir_lvl:
                    if i > level:
                        last_dir_lvl[i] = ''

                list_paths.append(last_dir_lvl[level] + content)
                depth = level

                if is_directory:
                    last_dir_lvl[depth + 1] = last_dir_lvl[depth] + content
            else:
                raise Exception('Ooops! Malformed directory tree !')

        return list_paths


def read_meta(meta) -> dict:
    dict_meta = {}
    # First part of template must refer a meta structure
    if 'meta' in meta:
        for line in meta.split('\n'):
            if ':' in line:
                data = line.split(':')
                dict_meta[data[0].strip()] = data[1].strip()
        return dict_meta
    else:
        raise Exception('Ooops! Template needs a metadata!')


def write_files(file_path: [str], cont: [str]):
    for fp in file_path:
        dir = re.search(r'.+/$', fp)
        if dir:
            # criação da dir
            os.mkdir(dir.string, mode=0o777)
        else:
            # criação do file
            with open(fp, 'w+', encoding='utf-8') as file:
                filename = fp.split('/')[-1]
                for c in cont:
                    search = re.search(f'^{filename}\n+', c.strip())
                    if (search):
                        info = search.string.split(f'{search[0]}')
                        for i in info:
                            file.write(i)


def main():
    if len(sys.argv) > 1:
        name = sys.argv[2]
        template = open(sys.argv[1], 'r', encoding='utf-8')
        contents_template = template.read()
        contents_template = re.sub(r'\{%name%\}', f'{name}', contents_template)
        template.close()

        list_contents_template = contents_template.split('===')
        more_contents = list_contents_template[3:]
        dict_meta = read_meta(list_contents_template[1])

        for key, value in dict_meta.items():
            for i in range(len(more_contents)):
                content = more_contents[i]
                replace_str = '{%' + key + '%}'
                more_contents[i] = content.replace(replace_str, value)
        #print(dict_meta)

        list_tree = create_tree(list_contents_template[2])
        #print(list_tree)

        write_files(list_tree, more_contents)

    else:
        raise Exception('Ooops! Only works with a template file!')


main()
