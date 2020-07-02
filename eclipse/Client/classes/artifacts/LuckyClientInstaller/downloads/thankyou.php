<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/home.css"/>
    
    <?php

        if(!isset($_GET['version_max']) && !isset($_GET['version_min'])) 
        {
            header("Location: /downloads", false);
        }
        $max = strval($_GET['version_max']);
        $min = strval($_GET['version_min']);
        $versao = "$max.$min";
    
    ?>
    </head>
    <body>
    <nav>
            <ul class="menu">
                <li><a class="nava" href="/">Home</a></li>
                <li><a class="nava" href=".">Download</a></li>
                <li><a class="nava" href="https://discord.gg/yB5GA5S">Discord</a></li>
            </ul>
        </nav>
    
       <h2><?php echo "Obrigado por baixar o LuckyMod $versao";?></h2>;
    
    </body>
</html>