export function redirectToSignIn() {
  console.info('redirect to home page for sign in')
  window.location = `${location.protocol}//${location.host}${location.pathname}`
}
