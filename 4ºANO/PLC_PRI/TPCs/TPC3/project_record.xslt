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
    <xsl:template match="META">
        <table width="100%" border="0">
            <tr>
                <td width="50%"> <b>KEY NAME: </b> <font color="#000080"> <xsl:value-of select="KEY_NAME"/> </font> </td>
                <td width="50%"> <b>BEGIN DATE: </b> <font color="#000080"> <xsl:value-of select="BEGIN_DATE"/></font></td>
            </tr>
            <tr>
                <td width="50%"> <b>TITLE: </b> <font color="#000080"> <xsl:value-of select="TITLE"/></font> </td>
                <td width="50%"><b>END DATE: </b> <font color="#000080"> <xsl:value-of select="END_DATE"/></font></td>   
            </tr>    
            <tr>
                <xsl:if test="SUBTITLE">
                    <td width="50%"> <b>SUBTITLE: </b> <font color="#000080"> <xsl:value-of select="SUBTITLE"/></font> </td>
                </xsl:if>                
                <td width="50%"> <b>SUPERVISOR: </b><font color="#000080"><a href="{SUPERVISOR/@href}"><xsl:value-of select="SUPERVISOR"/></a> </font> </td>   
            </tr> 
        </table>
        <hr/>
        <hr/>
    </xsl:template>

    <!-- TEMPLATE PARA WORKTEAM .....................................................-->
    <xsl:template match="WORKTEAM">
        <h3>WorkTeam: </h3>
        <ol>
            <xsl:apply-templates select="MEMBER"/>
        </ol>
        <hr/>
        <hr/>
    </xsl:template>
    
    <xsl:template match="MEMBER">
        <xsl:value-of select="NUMBER"/>
        .
        <xsl:value-of select="NAME"/>
        -
        <a href="mailto:{EMAIL/@href}"><xsl:value-of select="EMAIL"/></a>        
    </xsl:template>
    
    <!-- TEMPLATE PARA ABSTRACT .....................................................-->
    <xsl:template match = "ABSTRACT">  
        <h3>Abstract:</h3>
        <xsl:apply-templates select="PARAGRAPH"/>
        <hr/>
        <hr/>
    </xsl:template>
    
    <xsl:template match="TEXT">
        <p><xsl:apply-templates/></p>
    </xsl:template>
    
    <xsl:template match="BOLD">
        <b><xsl:apply-templates/></b>
    </xsl:template>

    <xsl:template match="ITALIC">
        <i><xsl:apply-templates/></i>
    </xsl:template>

    <xsl:template match="UNDERLINE">
        <u><xsl:apply-templates/></u>
    </xsl:template>

    <xsl:template match="LINK">
        <a href="{LINK/@href}"><xsl:value-of select="LINK"/></a>
    </xsl:template>
    
    <!-- TEMPLATE PARA DELIVERABLES .....................................................-->
    <xsl:template match="DELIVERABLES">
        <h3>DELIVERABLES: </h3>
        <ul>
            <xsl:apply-templates select="DELIVERABLE"/>
        </ul>
        <hr/>    
    </xsl:template>
    
    <xsl:template match="DELIVERABLE">
        <li>
            <a href="{@href}"><xsl:apply-templates/> </a>
        </li>
    </xsl:template>
    
    <!-- TEMPLATE PARA DATE .....................................................-->
    <xsl:template match="DATE">
        <p><xsl:apply-templates/></p>
    </xsl:template>
    
</xsl:stylesheet>