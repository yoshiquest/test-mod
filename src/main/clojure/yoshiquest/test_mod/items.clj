(ns yoshiquest.test-mod.items
  (:require
    [forge-clj.items :refer [defitem deftool defarmor deffood]]
    [forge-clj.util :refer [remote?]]
    [forge-clj.network :refer [send-to-server]]
    [yoshiquest.test-mod.network :refer [test-mod-server-network]])
  (:import
    [net.minecraft.creativetab CreativeTabs]
    [net.minecraft.potion Potion]))

(defitem test-item
         :creative-tab CreativeTabs/tabMisc)

(def test-material
  {:name             "test-material"
   :texture-name     "test-mod:test-material"
   :harvest-level    1
   :durability       100
   :mining-speed     4
   :damage           0
   :enchantability   10
   :damage-reduction {:helmet     5
                      :chestplate 7
                      :leggings   4
                      :boots      3}})

(deftool test-shovel :shovel test-material)

(defarmor test-boots :boots (assoc test-material :durability 10))

(deffood test-food 4 0.7
         :potion-effect [(.-id Potion/confusion) 20 0 1.0])

;Right click function for the net-test item. Simply sends a message to the server.
(defn right-click-send [istack world player]
  (if (remote? world)
    (send-to-server test-mod-server-network {:message "Hello World"}))
  istack)

;Test item to test the network system.
(defitem net-test
         :creative-tab CreativeTabs/tabMisc
         :override {:on-item-right-click right-click-send})