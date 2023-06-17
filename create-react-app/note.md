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
* 右对齐：`style={{ float: "right", margin: 8 }}`

# Component

* pass argument to Component
  * `const func = (props) => {}`
  * Then, `props.xxx`
  * Or, `const func = ({ xxx }) => {}`
* Ant Design Pro Components: 
  * 可以直接把整个页面搬过来使用
  * 并没有再次封装成新的组件 </>
  * 感觉并不太建议，需要熟练掌握文档阅读法 & 源码阅读法，要改很多东西

* 条件渲染:

  ```javascript
  {item.id && (<></>)}
  ```

# React

* useEffect: 
  * []: 什么也不监控，只触发一次
  * 不写: 监控所有

# Route: react-router-dom v6

* Basic Route:
  * use `<Routes>` rather than`<Switch>`
  * [Official document](https://reactrouter.com/en/6.9.0/upgrading/v5#upgrade-all-switch-elements-to-routes)

* Redirect:
  * use `useNavigate` rather than `useHistory`

    ``` javascript
    let navigate = useNavigate();
    navigate('success');
    ```
    
* Dynamic URL:
  * `useParams`

    ```javascript
    const { id } = useParams();
    ```

* `<a>` vs `<Link>`:
  * `<a>`: reload the entire page
  * `<Link`: just renders the new component

# Window

* refresh page: `window.location.reload();`

# Register & Login

1. 后端接收登录请求，查询是否登陆成功：
   * 成功：
     * 返回脱敏用户信息 `getSaftyUser()` 
     * 并将此信息作为键值对，存在 `session` 中
   * 失败：返回失败code
2. 后端创建 `getCurrentUser()` 的api：
   * 从 `session` 中取出键值对
   * 存在：返回脱敏用户信息
   * 不存在：返回 `null`
3. 前端访问时，使用后端 `getCurrentUser()` 确定是否登录，并保存在全局变量中
4. 注销：后端从 `session` 中删除键值对
5. 这里不用考虑 cookie 的部分，是因为这些由springboot自动完成了：
   * springboot 自动为会话生成一个唯一标识符，名为 `JSESSIONID` cookie，发送给浏览器
   * 浏览器后续请求时，会自动讲该 cookie 包含在请求头中
   * 后端接收到请求，通过 `JSESSIONID` 的 cookie 识别会话，获取对应的会话数据
   * 确保了正确关联用户的会话，不会混淆