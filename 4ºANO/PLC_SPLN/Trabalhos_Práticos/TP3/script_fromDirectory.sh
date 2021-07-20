#!/bin/bash

echo 'Reading directory docs Diário de Bordo'
sleep 1
python3 mkfromDirectory.py tests/mkfromDirectory/docs
echo '----'

echo 'Reading directory Ficheiros das aulas'
sleep 1
python3 mkfromDirectory.py tests/mkfromDirectory/ficheiros_aulas
echo '----'

echo 'Reading directory teste_tree example'
sleep 1
python3 mkfromDirectory.py tests/mkfromDirectory/tree_teste
echo '----'
