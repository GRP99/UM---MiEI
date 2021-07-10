<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title> Project Record </title>
            </head>
            <body>
                <h1 align="center">Project Record</h1>
                <hr/>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <!-- TEMPLATE PARA META .....................................................-->
    <xsl:template match="meta">
        <table width="100%" border="0">
            <tr>
                <td width="50%"> <b>KEY NAME: </b> <font color="#000080"> <xsl:value-of select="key"/> </font> </td>
                <td width="50%"> <b>BEGIN DATE: </b> <font color="#000080"> <xsl:value-of select="bdate"/></font></td>
            </tr>
            <tr>
                <td width="50%"> <b>TITLE: </b> <font color="#000080"> <xsl:value-of select="title"/></font> </td>
                <td width="50%"><b>END DATE: </b> <font color="#000080"> <xsl:value-of select="edate"/></font></td>   
            </tr>    
            <tr>
                <xsl:if test="subtitle">
                    <td width="50%"> <b>SUBTITLE: </b> <font color="#000080"> <xsl:value-of select="subtitle"/></font> </td>
                </xsl:if>                
                <td width="50%"> <b>SUPERVISOR: </b><font color="#000080"><a href="{supervisor/@url}"><xsl:value-of select="supervisor"/></a> </font> </td>   
            </tr> 
        </table>
        <hr/>
        <hr/>
    </xsl:template>

    <!-- TEMPLATE PARA WORKTEAM .....................................................-->
    <xsl:template match="workteam">
        <h3>WorkTeam: </h3>
        <ol>
            <xsl:apply-templates select="worker"/>
        </ol>
        <hr/>
        <hr/>
    </xsl:template>
    
    <xsl:template match="worker">
        <xsl:value-of select="@num"/>
        .
        <xsl:value-of select="name"/>
        -
        <a href="mailto:{email}"> <xsl:value-of select="email"/> </a>        
    </xsl:template>
    
    <!-- TEMPLATE PARA ABSTRACT .....................................................-->
    <xsl:template match = "abstract">  
        <h3>ABSTRACT:</h3>
        <xsl:apply-templates select="p"/>
        <hr/>
        <hr/>
    </xsl:template>
    
    <xsl:template match="p">
        <p><xsl:apply-templates/></p>
    </xsl:template>
    
    <xsl:template match="b">
        <b><xsl:apply-templates/></b>
    </xsl:template>

    <xsl:template match="i">
        <i><xsl:apply-templates/></i>
    </xsl:template>

    <xsl:template match="u">
        <u><xsl:apply-templates/></u>
    </xsl:template>

    <xsl:template match="link">
        <a href="{@url}"> <xsl:apply-templates/> </a>
    </xsl:template>
    
    <!-- TEMPLATE PARA DELIVERABLES .....................................................-->
    <xsl:template match="deliverables">
        <h3>Deliverables: </h3>
        <ul>
             <xsl:apply-templates select="link" mode="deliverable"/> 
        </ul>
        <hr/>    
    </xsl:template>
    
    <xsl:template match="link" mode="deliverable">
        <li> <a href="{@url}"> <xsl:apply-templates/> </a> </li>
    </xsl:template>
    
</xsl:stylesheet>