import genrl

g = genrl.importyamlgen("jj.yaml")
#print(genrl.pai("Ijj", g))

def test_pai():
  assert genrl.pai("Ijj", g) == "Duarte PDA"