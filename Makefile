CC=javac
FILES= ./src/Client.java ./src/FooPileRPL.java ./src/ObjetEmpilable.java ./src/OptionsManager.java ./src/PileRPL.java
DIR=`pwd`
DIST= puydoyeux_vincent-CalcRPL

CalcRPL:
	$(CC) $(FILES) -d ./bin
clean:
	rm -rf ./src/*.class
	rm -f ./bin/*.class	
dist: clean
	mkdir ../$(DIST)
	cp -r ../$(DIR)/*  ../$(DIST)
	rm -rf ../$(DIST).tar.gz 2>/dev/null
	tar cvfj ../$(DIST).tar.gz ../$(DIST) 
	rm -rf ../$(DIST)

