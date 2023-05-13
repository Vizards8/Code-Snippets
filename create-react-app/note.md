# Google Map API

## Get API key
* [Google Cloud Console](https://console.cloud.google.com/getting-started)
* Create a project
* Sidebar navigation, click on "`APIs & Services`"
* Click "`ENABLE APIS AND SERVICES`"
* Enable "`Maps JavaScript API`"
* Click on the "`CREATE CREDENTIALS`" button and select "`API key`"
* Contrats! Now you have generated the API key
* `AIzaSyAxZU2Dc2ePb2sJz8fwSArjwyeTPs1vZA8`

## google-map-react
* comment `</React.StrictMode>` in index.js if u want to display map

# CSS
* make it center: `margin: auto;`

# Component
* pass argument to Component
  * `const func = (props) => {}`
  * Then, `props.xxx`
  * Or, `const func = ({ xxx }) => {}`

# Route: react-router-dom v6
* Basic Route:
  * use `<Routes>` rather than`<Switch>`
  * [Official document](https://reactrouter.com/en/6.9.0/upgrading/v5#upgrade-all-switch-elements-to-routes)

* Redirect: 
  * use `useNavigate` rather than `useHistory`
  * ``` javascript
    let navigate = useNavigate();
    navigate('success');
    ```
    
* Dynamic URL: 
  * `useParams`
  * ```javascript
    const { id } = useParams();
    ```

* `<a>` vs `<Link>`:
  * `<a>`: reload the entire page
  * `<Link`: just renders the new component

# Window
* refresh page: `window.location.reload();`