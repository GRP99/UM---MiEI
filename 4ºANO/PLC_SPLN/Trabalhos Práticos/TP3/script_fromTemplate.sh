#!/bin/bash

echo 'Reading template 1'
sleep 1
python3 mkfromTemplate.py tests/mkfromTemplate/template template
echo '----'

echo 'Reading template 2'
sleep 1
python3 mkfromTemplate.py tests/mkfromTemplate/template2 template2
echo '----'

echo 'Reading template 3'
sleep 1
python3 mkfromTemplate.py tests/mkfromTemplate/template3 template3
echo '----'

echo 'Reading wrong template 1 - Malformed Tree'
sleep 1
python3 mkfromTemplate.py tests/mkfromTemplate/wrong_template malformed_tree
echo '----'

echo 'Reading wrong template 2 - Tree declared before meta'
sleep 1
python3 mkfromTemplate.py tests/mkfromTemplate/wrong_template2 tree_before_meta
echo '----'
