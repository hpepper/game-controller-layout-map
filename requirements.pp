# Usage: sudo puppet apply requirements.pp
package { 'gcc': ensure => present }
package { 'libxml2': ensure => present }
package { 'libxml2-dev': ensure => present }
package { 'libpdf-api2-perl': ensure => present }

