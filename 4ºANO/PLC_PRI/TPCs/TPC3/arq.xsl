<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <xsl:result-document href="arq-Website/tableofcontents.html">
            <html>
                <style>
                h1{
                    text-align: center;
                    font-size:50px;
                }
                h3{
                    margin-left: 13px;
                }
                body {
                    background-color: LightGray;
                }
                footer {
                    text-align: center;
                    padding: 3px;
                }
                </style>
                <head>
                    <title>Arquivo de Arqueossítios do Nordeste Português</title>
                </head>
                <body>
                    <div>
                        <h1 style="border: 2px solid white;"> Arqueossítios do Nordeste Português </h1>
                    </div>
                    <u><h3> Conteúdo: </h3></u>
                    <ul>
                        <xsl:apply-templates mode="index" select="//ARQELEM">
                            <xsl:sort select="CONCEL"/>
                            <xsl:sort select="FREGUE"/>
                            <xsl:sort select="LUGAR"/>
                            <xsl:sort select="IDENTI"/>
                        </xsl:apply-templates>
                    </ul>
                    <hr/>
                    <footer>
                        <p> Autor: Gonçalo Rodrigues Pinto </p>
                        <p>MiEI - 4ºAno - 1ºSemestre - PRI 20/21</p>
                        <p>
                            <a href="mailto:a83732@alunos.uminho.pt">a83732@alunos.uminho.pt</a>
                        </p>
                        <a href="https://github.com/GRP99/PRI2020" target="_blank">
                            <img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Logo.png" alt="Github" style="width:100px;height:50px;"></img>
                        </a>
                    </footer>
                </body>
            </html>
        </xsl:result-document>
        <xsl:apply-templates/>
    </xsl:template>

    <!-- TEMPLATE PARA O INDICE ................................................................-->
    <xsl:template match="ARQELEM" mode="index">
        <style>
            a:link {
                text-decoration: none;
            }
            a:hover {
                text-decoration: underline;
            }
            
            a:active {
                text-decoration: underline;
            }
        </style>
        <li>
            <a name="arq-{generate-id()}"/>
            <a style="font-weight:bold" href="arq-{generate-id()}.html">
                <xsl:value-of select="CONCEL"/>
                -
                <xsl:value-of select="FREGUE"/>
                -
                <xsl:value-of select="LUGAR"/>
                :
                <xsl:value-of select="IDENTI"/>
            </a>
        </li>
    </xsl:template>

    <!-- TEMPLATE PARA O CONTEUDO ................................................................-->
    <xsl:template match="ARQELEM">
        <xsl:result-document href="arq-Website/arq-{generate-id()}.html">
            <html>
                <style>
                    h2{
                        font-size: 24;
                        text-align: center;
                    }
                    body {
                        background-color: LightGray;
                    }
                    footer {
                        text-align: center;
                        padding: 3px;
                        color: blue;
                    }
                </style>
                <head>
                    <title> Arqueossítio : <xsl:value-of select="IDENTI"/>
                    </title>
                </head>
                <meta name="viewport" content="width=device-width, initial-scale=1"/>
                <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"/>
                <body>
                    <div class="w3-container">
                        <h2>
                            <xsl:value-of select="IDENTI"/>
                        </h2>
                        <table class="w3-table-all w3-card-4">
                            <tr>
                                <th>TIPO</th>
                                <td>
                                    <xsl:value-of select="TIPO/@ASSUNTO"/>
                                </td>
                            </tr>
                            <xsl:if test="IMAGEM">
                                <tr>
                                    <th>IMAGEM</th>
                                    <td>
                                        <img src="{IMAGEM/@NOME}">
                                            <xsl:value-of select="IMAGEM/@NOME"/>
                                        </img>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="DESCRI">
                                <tr>
                                    <th>DESCRIÇÃO</th>
                                    <td>
                                        <xsl:apply-templates select="DESCRI"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="CRONO">
                                <tr>
                                    <th>CRONOLOGIA</th>
                                    <td>
                                        <xsl:value-of select="CRONO"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="LUGAR">
                                <tr>
                                    <th>LUGAR</th>
                                    <td>
                                        <xsl:value-of select="LUGAR"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="FREGUE">
                                <tr>
                                    <th>FREGUESIA</th>
                                    <td>
                                        <xsl:value-of select="FREGUE"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="CONCEL">
                                <tr>
                                    <th>CONCELHO</th>
                                    <td>
                                        <xsl:value-of select="CONCEL"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="CODADM">
                                <tr>
                                    <th>CODADM</th>
                                    <td>
                                        <xsl:value-of select="CODADM"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="LATITU">
                                <tr>
                                    <th>LATITUDE</th>
                                    <td>
                                        <xsl:value-of select="LATITU"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="LONGIT">
                                <tr>
                                    <th>LONGITUDE</th>
                                    <td>
                                        <xsl:value-of select="LONGIT"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="ALTITU">
                                <tr>
                                    <th>ALTITUDE</th>
                                    <td>
                                        <xsl:value-of select="ALTITU"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="ACESSO">
                                <tr>
                                    <th>ACESSO</th>
                                    <td>
                                        <xsl:value-of select="ACESSO"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="QUADRO">
                                <tr>
                                    <th>ENQUADRAMENTO</th>
                                    <td>
                                        <xsl:value-of select="QUADRO"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="TRAARQ">
                                <tr>
                                    <th>TRABALHOS ARQUEOLÓGICOS</th>
                                    <td>
                                        <xsl:value-of select="TRAARQ"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="DESARQ">
                                <tr>
                                    <th>DESCRIÇÃO ARQUEOLÓGICA</th>
                                    <td>
                                        <xsl:value-of select="DESARQ"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="INTERP">
                                <tr>
                                    <th>INTERPRETAÇÃO</th>
                                    <td>
                                        <xsl:value-of select="INTERP"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="DEPOSI">
                                <tr>
                                    <th>DEPÓSITO</th>
                                    <td>
                                        <xsl:value-of select="DEPOSI"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="INTERE">
                                <tr>
                                    <th>INTERESSE ARQUEOLÓGICO</th>
                                    <td>
                                        <xsl:value-of select="INTERE"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="BIBLIO">
                                <tr>
                                    <th>BIBLIOGRAFIA</th>
                                    <td>
                                        <xsl:value-of select="BIBLIO"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="AUTOR">
                                <tr>
                                    <th>AUTOR</th>
                                    <td>
                                        <xsl:value-of select="AUTOR"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="DATA">
                                <tr>
                                    <th>DATA</th>
                                    <td>
                                        <xsl:value-of select="DATA"/>
                                    </td>
                                </tr>
                            </xsl:if>
                        </table>
                    </div>
                    <footer>
                        <address>[<a href="tableofcontents.html#arq-{generate-id()}">Voltar ao conteúdo</a>]</address>
                    </footer>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>
</xsl:stylesheet>