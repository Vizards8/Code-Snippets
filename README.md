# Code-Snippets
This is a personal code snippet repository for programming languages and frameworks. It serves as a reference for common development tasks like building a website. Snippets are not complete solutions but rather starting points.

## Git clone subdirectory

```bash
git init <directory_name>
cd <directory_name>
git remote add origin <repository_url>
git config core.sparseCheckout true
echo "react/*" >> .git/info/sparse-checkout
git pull origin master
```

## Small tool to delete deployment tab in Github

[remove_deployments_tab.py](Tools/remove_deployments_tab.py)