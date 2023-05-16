# Vercel

## Install Vercel CLI (optional)

  ```bash
  npm install -g vercel
  ```

## Build your application

  ```bash
  npm run build
  ```

## Deploy to Vercel
* Using the Vercel website:
  * just import your github repository 
* Using the Vercel CLI:
  * navigate to your project's root directory

    ```bash
    vercel
    ```

## Connect to bakend API
* add `vercel.json`
* http:localhost:3000/v1_0/hello -> http://your_ip/hello

  ```
  {
    "rewrites": [
      {
        "source": "/v1_0/:path*",
        "destination": "http://your_ip/:path*"
      }
    ]
  }
  ```