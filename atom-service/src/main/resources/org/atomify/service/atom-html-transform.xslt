<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<!--

    Copyright (c) 2009 Stephan Schloepke and innoQ Deutschland GmbH

    Stephan Schloepke: http://www.schloepke.de/
    innoQ Deutschland GmbH: http://www.innoq.com/

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

-->

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:atom="http://www.w3.org/2005/Atom"
  xmlns:app="http://www.w3.org/2007/app"
  xmlns:xhtml="www.w3.org/1999/xhtml">

  <xsl:template match="app:service">
    <html xmlns="www.w3.org/1999/xhtml">
      <head>
        <title>DVB Bank Service Discovery</title>
      </head>
      <body>
        <h1>Service Document</h1>
        <xsl:apply-templates />
      </body>
    </html>
  </xsl:template>

  <xsl:template match="app:workspace">
      <!-- Here we sort of have a problem since the title can be HTML in a non wellformed xml way -->    
      <h2 xmlns="www.w3.org/1999/xhtml"><xsl:apply-templates select="atom:title" /></h2>
      <ul>
        <xsl:apply-templates select="app:collection" />
      </ul>
  </xsl:template>
  
  <xsl:template match="app:collection">
    <li xmlns="www.w3.org/1999/xhtml">
      <a href="{@href}"><xsl:apply-templates select="atom:title" /></a>
    </li>
  </xsl:template>

</xsl:stylesheet>
