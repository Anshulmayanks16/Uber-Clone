{
  "functions": {
    "predeploy": [
    ]
  },
  "hosting": {
    "public": "public",
    "ignore": [
      "firebase.json",
      "**/.*",
      "**/node_modules/**"
    ],
    "rewrites": [
      {
        "source": "/createStripeConnectAccount",
        "function": "createStripeConnectAccount"
      }
    ]
  }
}
