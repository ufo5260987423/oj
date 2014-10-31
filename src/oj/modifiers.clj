(ns oj.modifiers
  (:use inflections.core))

(defn query
  "Creates a skeletal query map with the given table name."
  [table-name]
  {:table table-name})

(defn where
  "Modifies the query map to include the :where clause provided"
  [query wheres]
  (assoc query :where wheres))

(defn limit
  "Modifies the query map to include an :limit"
  [query n]
  (assoc query :limit n))

(defn order
  "Modifies the query map to include the :order clause provided"
  [query order-by]
  (assoc query :order order-by))

(defn select
  "Modifies the query map to include the :select clause provided"
  [query columns]
  (assoc query :select columns))

(defn join
  "Modifies the query map to include the :join clause provided. If no join
  columns are specified, it will make a guess:
    (singlular :table)_id => id"
  ([q join-name on]
    (assoc-in q [:join (keyword join-name)]
      (-> (query join-name)
          (where on))))
  ([q join-name]
    (assoc-in q [:join (keyword join-name)]
      (let [foreign-key (str (name (singular (:table q))) "_id")]
        (-> (query join-name)
            (where {(keyword foreign-key) :id}))))))
