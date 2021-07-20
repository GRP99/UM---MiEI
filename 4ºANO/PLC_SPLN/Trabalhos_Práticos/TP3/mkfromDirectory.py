import os, sys, yaml
from jjcli import *


# Creates tree from a startpath
def mk_tree(startpath, files_content) -> str:
    str = ''
    for root, dirs, files in os.walk(startpath):
        level = root.replace(startpath, '').count(os.sep)
        indent = '-' * (level)
        if level:
            indent += ' '
        str += '{}{}/\n'.format(indent, os.path.basename(root))
        subindent = '-' * (level + 1)
        for f in files:
            # Path de cada ficheiro
            f_path = './' + root + '/' + f
            # if file is not empty, and diferent from meta.txt
            if not os.stat(f_path).st_size == 0 and f_path != f'./{startpath}/meta':
                # reads
                r = open(f_path, 'r', encoding='utf-8', errors='ignore').read()
                if f not in files_content:
                    files_content[f] = r
                else:
                    raise Exception('ERROR: 2 non-empty files defined with the same name')

            str += '{} {}\n'.format(subindent, f)
    return str


# Creating template
def create_template(dir, meta, tree, files_content):
    dir = re.split('/', dir)[-1]
    dir = re.sub('[^A-Za-z0-9-\_]+', '', dir.lower())
    with open(f'template-{dir}.txt', mode='w+', encoding='utf8') as template:
        template.write('=== meta\n\n')
        for k, v in meta.items():
            template.write(f'{k}: {v}\n')

        template.write('\n\n=== tree\n\n')
        template.write(tree)

        for f, c in files_content.items():
            template.write(f'\n\n=== {f}\n\n')
            for k, v in meta.items():
                # sub between text
                c = re.sub(fr'{v}(\s+)', fr'{{%{k}%}}\1', c)
                # sub at the end
                c = re.sub(fr'{v}$', fr'{{%{k}%}}', c)
            template.write(c)


def main():
    if len(sys.argv) > 1:

        tree = ''
        files_content = {}

        if (os.path.isdir(sys.argv[1])):
            dir_ = sys.argv[1]
            tree = mk_tree(dir_, files_content)
        else:
            raise Exception('Ooops! The argument given is not a dir.')

        # Checking meta file
        if not os.path.exists(dir_ + '/meta'):
            raise Exception('Ooops! No meta file defined.')
        else:
            try:
                with open(dir_ + '/meta') as f:
                    meta = yaml.full_load(f)
            except:
                raise Exception("Error trying to load the config file in YAML format")

        create_template(dir_, meta, tree, files_content)
    else:
        raise Exception('Ooops! Only works with a start path !')


main()
