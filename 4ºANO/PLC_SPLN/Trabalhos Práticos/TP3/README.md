# Template multi-file

For several software projects, solutions involving several files, several folders are common. Example: a file, a makefile, a manual, an example folder, etc. 

## mkfromTemplate
Program "mkfromTemplate" is capable of accepting a project name, and a description file of a template-multi-file and which creates the project's initial files and folders.

The template includes:
* metadata (author, email) to replace in the following elements
* tree (directory structure and smells to be created)
* template of each file

How to run:
```py
$ python mkfromTemplate.py $template_file $project_name
```

## mkfromDirectory
The "mkfromDirectory" program is capable of accepting files and folders from a project and generating a multi-file template for these parameters.

The directory includes:
* file "meta" with metadata
* files and folders

How to run:
```py
$ python mkfromDirectory.py $path_folder
```
