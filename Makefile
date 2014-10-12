PAGES=../org.lappsgrid.serialization.pages

help:
	@echo "Help is needed..."
	
clean:
	mvn clean
	
jar:
	mvn package
	
deploy:
	mvn javadoc:jar source:jar deploy
	
site:
	cd $(PAGES) ; git checkout gh-pages ; git pull
	mvn site
	cp -r target/site/* $(PAGES)
	cd $(PAGES) ; git add . ; commit "Updated Javadocs" ; push

