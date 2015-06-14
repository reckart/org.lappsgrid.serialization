PAGES=../org.lappsgrid.serialization.pages

help:
	@echo "Help is needed..."
	
clean:
	mvn clean
	
jar:
	mvn package
	
deploy:
	mvn javadoc:jar source:jar deploy
	
docs:
	if [ -e target/apidocs ] ; then rm -rf target/apidocs ; fi
	lappsdoc "Lappsgrid Serialization"

site:
	cd $(PAGES) ; git checkout gh-pages ; git pull
	#mvn site
	if [ -e target/apidocs ] ; then rm -rf target/apidocs ; fi
	lappsdoc "Lappsgrid Serialization"
	cp -r target/apidocs/* $(PAGES)
	cd $(PAGES) ; git add . ; commit "Updated Javadocs" ; push -b

