
<h3>Pretty logs:</h3>

git log --pretty='%C(red)%h%Creset%C(yellow)%d%Creset %s %C(cyan)(%ar)%Creset %C(blue)[%an]%Creset'

or create an alias:

git config --global alias.lg = log --pretty='%C(red)%h%Creset%C(yellow)%d%Creset %s %C(cyan)(%ar)%Creset %C(blue)[%an]%Creset'

Commit part of a file:

>git add --patch

it will ask - select split (s)

later use which part to add - y or n

---

Search for commits with message part:

>git lg -S 'fix'

---

Search for changes related to specific method:

>git lg -L:record:src/main/java/com/xpinjection/library/service/dto/Recommendation.java

Where a record is the name to search, and later you pass the path to the file itself

---

Add change to the previous commit:

>git commit --fixup <hash-for-needed-commit>

>git rebase --interactive --root

---

see what do you have new at your current branch

>git lg main..feature/my_feature

or what is new at main - that isn't presented at the feature branch

>git lg feature/my_feature..main
