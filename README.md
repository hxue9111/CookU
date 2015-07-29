# CookU
CodeU Final Project - Search recipes based on ingredients you have!

Google Doc: https://docs.google.com/document/d/1Zf3IkstqBkmMxjLATQFFAIwvjbszTAFB97flhdWqNvg/

####Make sure we conform to reqs! https://www.udacity.com/wiki/ud853/project

###Currently Working on:

Huang- AsyncTaskLoader that can calls backend and generates list of RecipeItems

###TODOS:
```
-Create SettingsActivity to manage settings (like grid/list recipe view)

-Make recipe list on RecipeSearchFragment be populated by Cursor from ContentProvider (cursorloader?)

-Make RecipeSearchFragment add ingredients to database and update list

-Make Search button on RecipeSearchFragment do callback to MainActivity to create a AsyncTaskLoader that can call the backend and generate list of recipe items(recipe name, recipe url, thumbnail url) and pass loader to RecipeListFragment

-Check preferences and load correct layout in RecipeListFragment after data is loaded

-If scrolled to bottom of page on RecipeListFragment, do trigger loader to load more recipes

-If element is clicked on RecipeListFragment, do callback to MainActivity and launch a RecipeDetailsFragment
```

