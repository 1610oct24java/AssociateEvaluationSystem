#!/bin/bash
# GTK+ and Firefox for Amazon Linux
# Written by Vipin Chereekandy


# chmod 755 ./gtk-firefox.sh
# sudo ./gtk-firefox.sh


TARGET=/usr/local

function init()
{
export installroot=$TARGET/src
export workpath=$TARGET

yum --assumeyes install make libjpeg-devel libpng-devel \
libtiff-devel gcc libffi-devel gettext-devel libmpc-devel \
libstdc++46-devel xauth gcc-c++ libtool libX11-devel \
libXext-devel libXinerama-devel libXi-devel libxml2-devel \
libXrender-devel libXrandr-devel libXt dbus-glib \
libXdamage libXcomposite
mkdir -p $workpath
mkdir -p $installroot
cd $installroot
PKG_CONFIG_PATH="$workpath/lib/pkgconfig"
PATH=$workpath/bin:$PATH
export PKG_CONFIG_PATH PATH

bash -c "
cat << EOF > /etc/ld.so.conf.d/firefox.conf
$workpath/lib
$workpath/firefox
EOF
ldconfig
"
}

function finish()
{
cd $workpath
wget -r --no-parent --reject "index.html*" -nH --cut-dirs=7 http://download.cdn.mozilla.net/pub/mozilla.org/firefox/releases/latest/linux-x86_64/en-US/
tar xvf firefox*
cd bin
ln -s ../firefox/firefox
ldconfig
}

function install()
{
wget $1
FILE=`basename $1`
if [ ${FILE: -3} == ".xz" ]
then tar xvfJ $FILE
else tar xvf $FILE
fi
SHORT=${FILE:0:4}*
cd $SHORT
./configure --prefix=$workpath
make
make install
ldconfig
cd ..
}

init
install ftp://ftp.gnu.org/gnu/autoconf/autoconf-2.69.tar.xz
install http://download.savannah.gnu.org/releases/freetype/freetype-2.4.9.tar.gz
install http://www.freedesktop.org/software/fontconfig/release/fontconfig-2.9.0.tar.gz
install http://ftp.gnome.org/pub/gnome/sources/glib/2.32/glib-2.32.3.tar.xz
install http://cairographics.org/releases/pixman-0.26.0.tar.gz
install http://cairographics.org/releases/cairo-1.12.2.tar.xz
install http://ftp.gnome.org/pub/gnome/sources/pango/1.30/pango-1.30.0.tar.xz
install http://ftp.gnome.org/pub/gnome/sources/atk/2.4/atk-2.4.0.tar.xz
install http://ftp.gnome.org/pub/GNOME/sources/gdk-pixbuf/2.26/gdk-pixbuf-2.26.1.tar.xz
install http://ftp.gnome.org/pub/gnome/sources/gtk+/2.24/gtk+-2.24.10.tar.xz
finish


# adds the /usr/local/bin to your path by updating your .bashrc file.
cat << EOF >> ~/.bashrc
PATH=/usr/local/bin:\$PATH
export PATH
EOF
