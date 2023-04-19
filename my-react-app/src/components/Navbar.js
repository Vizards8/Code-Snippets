import React from "react";
import { Link } from "react-router-dom";
import { Dropdown, Menu } from "antd";
import "./Navbar.css";

const menu = (
  <Menu>
    <Menu.Item key="1" href="/profile">
      <Link to="/profile">Profile</Link>
    </Menu.Item>
    <Menu.Item key="2" href="/setting">
      <Link to="/setting">Settings</Link>
    </Menu.Item>
    <Menu.Item key="3" href="/">
      <Link to="/">Logout</Link>
    </Menu.Item>
  </Menu>
);

function Navbar() {
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "20px",
        backgroundColor: "#f8f9fa",
        boxShadow: "0 2px 2px rgba(0, 0, 0, 0.1)",
      }}
    >
      <div
        style={{ display: "flex", alignItems: "center", marginLeft: "70px" }}
      >
        <Link
          to="/"
          style={{
            display: "flex",
            alignItems: "center",
            fontWeight: "bold",
            marginRight: "20px",
            fontSize: "16px",
            color: "#212529",
            textDecoration: "none",
          }}
        >
          <img
            src="/images/logo.jpg"
            alt="logo"
            style={{
              height: "30px",
              borderRadius: "25%",
              marginRight: "10px",
            }}
          ></img>
          Name
        </Link>
        <Link to="/" className="navbar-link">
          Home
        </Link>
        <Link to="/locations" className="navbar-link">
          Locations
        </Link>
        <Link to="/menu1" className="navbar-link">
          Menu1
        </Link>
        <Link to="/menu2" className="navbar-link">
          Menu2
        </Link>
      </div>
      <div style={{ display: "flex", marginRight: "70px" }}>
        <form style={{ display: "flex" }}>
          <input
            type="text"
            placeholder="Search"
            style={{
              marginRight: "10px",
              padding: "5px 10px",
              fontSize: "16px",
              borderRadius: "5px",
              border: "none",
              boxShadow: "0 2px 2px rgba(0, 0, 0, 0.1)",
            }}
          />
          <button
            type="submit"
            style={{
              backgroundColor: "#007bff",
              color: "#fff",
              fontSize: "16px",
              padding: "5px 10px",
              borderRadius: "5px",
              border: "none",
              cursor: "pointer",
              boxShadow: "0 2px 2px rgba(0, 0, 0, 0.1)",
            }}
          >
            Search
          </button>
        </form>
        {/* <img
          src="/images/profile.png"
          alt="avatar"
          style={{
            height: "30px",
            borderRadius: "50%",
            marginLeft: "10px",
          }}
        /> */}
        <Dropdown overlay={menu}>
          <img
            src="/images/profile.png"
            alt="avatar"
            style={{
              height: "30px",
              borderRadius: "50%",
              marginLeft: "10px",
              cursor: "pointer",
            }}
          />
        </Dropdown>
      </div>
    </div>
  );
}

export default Navbar;
